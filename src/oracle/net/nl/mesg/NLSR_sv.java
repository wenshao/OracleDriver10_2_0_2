package oracle.net.nl.mesg;

import java.util.ListResourceBundle;

public class NLSR_sv extends ListResourceBundle
{
  static final Object[][] contents = { { "NoFile-04600", "TNS-04600: Filen finns inte: {0}" }, { "FileReadError-04601", "TNS-04601: Fel vid läsning av följande parameterfil: {0}, felet är: {1}" }, { "SyntaxError-04602", "TNS-04602: Ogiltig syntax: \"{0}\" förväntades innan eller vid {1}" }, { "UnexpectedChar-04603", "TNS-04603: Ogiltig syntax: Oväntat tecken, \"{0}\", vid analys av {1}" }, { "ParseError-04604", "TNS-04604: Analysobjektet är inte initierat" }, { "UnexpectedChar-04605", "TNS-04605: Ogiltig syntax: Oväntat tecken eller LITERAL, \"{0}\", innan eller vid {1}" }, { "NoLiterals-04610", "TNS-04610: Inga literaler är kvar, slutet av NV-paret nåddes" }, { "InvalidChar-04611", "TNS-04611: Ogiltigt fortsättningstecken efter kommentar" }, { "NullRHS-04612", "TNS-04612: Null-RHS för \"{0}\"" }, { "Internal-04613", "TNS-04613: Internt fel: {0}" }, { "NoNVPair-04614", "TNS-04614: NV-paret {0} hittades inte" }, { "InvalidRHS-04615", "TNS-04615: Ogiltig RHS för {0}" } };

  public Object[][] getContents()
  {
    return contents;
  }
}

/* Location:           /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2/jdbc.oracle-10.2.0.2.jar
 * Qualified Name:     oracle.net.nl.mesg.NLSR_sv
 * JD-Core Version:    0.6.0
 */