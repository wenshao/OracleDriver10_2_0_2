package oracle.net.ano;

import java.io.IOException;
import oracle.net.ns.NetException;
import oracle.net.ns.SQLnetDef;
import oracle.net.ns.SessionAtts;

public class SupervisorService extends Service implements SQLnetDef {
	private int[] h;
	static final int i = 47;
	static final int j = 3;
	static final int k = 127;
	private byte[] l;
	static final int m = 111;
	static final int n = 63;
	static final int o = 95;
	private int p;
	private int q;
	private int[] r;
	static final int s = 31;
	static final int t = 79;

	void i(String[] paramArrayOfString1, String[] paramArrayOfString2,
			int paramInt) throws NetException {
		this.selectedDrivers = new byte[paramArrayOfString1.length];
		this.selectedDrivers[0] = k(paramArrayOfString2,
				paramArrayOfString1[(paramArrayOfString1.length - 1)]);
		for (int i1 = 1; i1 < paramArrayOfString1.length; i1++)
			this.selectedDrivers[i1] = k(paramArrayOfString2,
					paramArrayOfString1[(i1 - 1)]);
	}

	int f() {
		return 12 + this.l.length + 4 + 10 + this.h.length * 2;
	}

	void d() throws NetException, IOException {
		this.version = this.comm.receiveVersion();
		this.status = this.comm.receiveStatus();
		if (this.status != 31)
			throw new NetException(306);
		this.r = this.comm.receiveUB2Array();
	}

	void n() throws NetException, IOException {
		this.comm.sendVersion();
		this.comm.sendRaw(this.l);
		this.comm.sendUB2Array(this.h);
	}

	void b() throws NetException, IOException {
		for (int i1 = 0; i1 < this.r.length; i1++) {
			for (int i2 = 0; i2 < this.h.length; i2++) {
				if (this.r[i1] != this.h[i2])
					continue;
				this.p += 1;
				break;
			}
			if (i2 != this.h.length)
				continue;
			throw new NetException(320);
		}
		if (this.p != this.q)
			throw new NetException(321);
	}

	int a(SessionAtts paramSessionAtts) throws NetException {
		super.a(paramSessionAtts);
		this.service = 4;
		this.serviceSubPackets = 3;
		this.l = g();
		this.p = 0;
		this.q = 2;
		this.availableDrivers = h("oracle.net.ano.", AnoServices.SERV_CLASSNAME);
		i(this.availableDrivers, AnoServices.SERV_CLASSNAME, this.level);
		this.h = new int[this.selectedDrivers.length];
		for (int i1 = 0; i1 < this.h.length; i1++) {
			this.h[i1] = 0;
			this.h[i1] |= 0xFF & this.selectedDrivers[i1];
		}
		return 1;
	}

	byte[] g() {
		byte[] arrayOfByte = new byte[8];
		for (int i1 = 0; i1 < arrayOfByte.length; i1++)
			arrayOfByte[i1] = 9;
		return arrayOfByte;
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name: oracle.net.ano.SupervisorService
 * JD-Core Version: 0.6.0
 */