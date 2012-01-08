package oracle.net.ns;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Properties;
import oracle.net.ano.Ano;
import oracle.net.nl.NLException;
import oracle.net.nl.NVFactory;
import oracle.net.nl.NVNavigator;
import oracle.net.nl.NVPair;
import oracle.net.nt.ConnOption;
import oracle.net.nt.NTAdapter;
import oracle.net.resolver.AddrResolution;

public class NSProtocol
  implements Communication, SQLnetDef
{
  private static final boolean ACTIVATE_ANO = true;
  private AddrResolution addrRes;
  private SessionAtts sAtts = new SessionAtts(32767, 32767);
  private MarkerPacket mkPkt;
  private Packet packet;

  public NSProtocol()
  {
    this.sAtts.connected = false;
  }

  public void connect(String paramString, Properties paramProperties)
    throws IOException, NetException
  {
    if (this.sAtts.connected)
      throw new NetException(201);
    if (paramString == null)
      throw new NetException(208);
    NVFactory localNVFactory = new NVFactory();
    NVNavigator localNVNavigator = new NVNavigator();
    NVPair localNVPair = null;
    String str1 = null;
    this.addrRes = new AddrResolution(paramString, paramProperties);
    if (this.addrRes.connection_revised)
    {
      paramString = this.addrRes.getTNSAddress();
      paramProperties = this.addrRes.getUp();
    }
    this.sAtts.profile = new ClientProfile(paramProperties);
    establishConnection(paramString);
    Object localObject1 = null;
    try
    {
      localObject1 = Class.forName("oracle.net.ano.Ano").newInstance();
      this.sAtts.anoEnabled = true;
    }
    catch (Exception localException1)
    {
      this.sAtts.anoEnabled = false;
    }
    if (localObject1 != null)
    {
      ((Ano)localObject1).init(this.sAtts);
      this.sAtts.ano = ((Ano)localObject1);
      this.sAtts.anoEnabled = true;
    }
    while (true)
    {
      localConnectPacket = new ConnectPacket(this.sAtts);
      localConnectPacket.send();
      this.packet = new Packet(this.sAtts, this.sAtts.getSDU());
      this.packet.receive();
      switch (this.packet.type)
      {
      case 2:
        localAcceptPacket = new AcceptPacket(this.packet);
        break;
      case 5:
        localRedirectPacket = new RedirectPacket(this.packet);
        localObject2 = this.sAtts.cOption;
        this.addrRes.connection_redirected = true;
        this.sAtts.cOption.nt.disconnect();
        this.sAtts = establishConnection(localRedirectPacket.getData());
        this.sAtts.cOption.restoreFromOrigCoption((ConnOption)localObject2);
        break;
      case 4:
        localRefusePacket = new RefusePacket(this.packet);
        this.sAtts.cOption.nt.disconnect();
        this.sAtts.cOption = null;
        establishConnection(null);
        if (this.sAtts.cOption != null)
          continue;
        try
        {
          localNVPair = localNVNavigator.findNVPairRecurse(localNVFactory.createNVPair(localRefusePacket.getData()), "ERROR");
          str1 = localNVNavigator.findNVPairRecurse(localNVPair, "CODE").valueToString();
        }
        catch (NLException localNLException)
        {
          System.err.println(localNLException.getMessage());
        }
        throw new NetException(Integer.parseInt(str1), paramString + "\n");
      case 11:
        break;
      case 3:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      default:
        this.sAtts.cOption.nt.disconnect();
        throw new NetException(205);
      }
    }
    setNetStreams();
    this.sAtts.connected = true;
    Object localObject2 = (String)this.sAtts.nt.getOption(6);
    if ((localObject2 != null) && (((String)localObject2).equalsIgnoreCase("false")))
      throw new NetException(405);
    if (this.sAtts.ano != null)
    {
      this.sAtts.ano.negotiation();
      String str2 = (String)this.sAtts.nt.getOption(2);
      if ((str2 != null) && (str2.equals("TRUE")))
        try
        {
          Method localMethod = this.sAtts.ano.getClass().getMethod("getEncryptionAlg", null);
          if (localMethod.invoke(this.sAtts.ano, null) != null)
            throw new NetException(406);
        }
        catch (Exception localException2)
        {
        }
    }
    this.packet = null;
    ConnectPacket localConnectPacket = null;
    AcceptPacket localAcceptPacket = null;
    RedirectPacket localRedirectPacket = null;
    RefusePacket localRefusePacket = null;
  }

  public void disconnect()
    throws IOException, NetException
  {
    if (!this.sAtts.connected)
      throw new NetException(200);
    Object localObject = null;
    try
    {
      this.sAtts.nsOutputStream.close();
    }
    catch (IOException localIOException)
    {
      localObject = localIOException;
    }
    this.sAtts.connected = false;
    this.sAtts.cOption.nt.disconnect();
    if (localObject != null)
      throw localObject;
  }

  public void sendBreak()
    throws IOException, NetException
  {
    if (!this.sAtts.connected)
      throw new NetException(200);
    sendMarker(1);
    this.mkPkt = null;
  }

  public void sendReset()
    throws IOException, NetException
  {
    if (!this.sAtts.connected)
      throw new NetException(200);
    sendMarker(2);
    while (this.sAtts.onBreakReset)
    {
      this.packet = new Packet(this.sAtts, this.sAtts.getSDU());
      this.packet.receive();
      if (this.packet.type != 12)
        continue;
      this.mkPkt = new MarkerPacket(this.packet);
      if (this.mkPkt.data != 2)
        continue;
      this.sAtts.onBreakReset = false;
    }
    this.mkPkt = null;
  }

  public InputStream getInputStream()
    throws NetException
  {
    if (!this.sAtts.connected)
      throw new NetException(200);
    return this.sAtts.nsInputStream;
  }

  public OutputStream getOutputStream()
    throws NetException
  {
    if (!this.sAtts.connected)
      throw new NetException(200);
    return this.sAtts.nsOutputStream;
  }

  private SessionAtts establishConnection(String paramString)
    throws NetException, IOException
  {
    this.sAtts.cOption = this.addrRes.resolveAndExecute(paramString);
    if (this.sAtts.cOption == null)
      return null;
    this.sAtts.nt = this.sAtts.cOption.nt;
    this.sAtts.ntInputStream = this.sAtts.cOption.nt.getInputStream();
    this.sAtts.ntOutputStream = this.sAtts.cOption.nt.getOutputStream();
    this.sAtts.setTDU(this.sAtts.cOption.tdu);
    this.sAtts.setSDU(this.sAtts.cOption.sdu);
    this.sAtts.nsOutputStream = new NetOutputStream(this.sAtts, 255);
    this.sAtts.nsInputStream = new NetInputStream(this.sAtts);
    return this.sAtts;
  }

  private void setNetStreams()
    throws NetException, IOException
  {
    this.sAtts.nsOutputStream = new NetOutputStream(this.sAtts);
    this.sAtts.nsInputStream = new NetInputStream(this.sAtts);
  }

  private void sendMarker(int paramInt)
    throws IOException, NetException
  {
    if (paramInt == 1)
      this.mkPkt = new MarkerPacket(this.sAtts);
    else
      this.mkPkt = new MarkerPacket(this.sAtts, paramInt);
    this.mkPkt.send();
  }

  public void setO3logSessionKey(byte[] paramArrayOfByte)
    throws NetException, NetException
  {
    if (paramArrayOfByte != null)
      this.sAtts.ano.setO3logSessionKey(paramArrayOfByte);
  }

  public void setOption(int paramInt, Object paramObject)
    throws NetException, IOException
  {
    if ((paramInt > 0) && (paramInt < 10))
    {
      NTAdapter localNTAdapter = this.sAtts.getNTAdapter();
      localNTAdapter.setOption(paramInt, paramObject);
    }
  }

  public Object getOption(int paramInt)
    throws NetException, IOException
  {
    if ((paramInt > 0) && (paramInt < 10))
    {
      NTAdapter localNTAdapter = this.sAtts.getNTAdapter();
      return localNTAdapter.getOption(paramInt);
    }
    return null;
  }

  public void abort()
    throws NetException, IOException
  {
    NTAdapter localNTAdapter = this.sAtts.getNTAdapter();
    if (localNTAdapter != null)
      localNTAdapter.abort();
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.ns.NSProtocol
 * JD-Core Version:    0.6.0
 */