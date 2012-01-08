package oracle.security.pki;

import java.util.Enumeration;

public class OracleSecretStore {
	public boolean containsAlias(String walletLocation) {
		return true;
	}
	public char[] getSecret(String walletLocation) {
		return null;
	}
	
	public Enumeration<String> internalAliases() {
		return null;
	}
}
