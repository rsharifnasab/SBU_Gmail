
package BasicClasses;

import java.io.*;

public class LoginInformation implements Serializable {


	private String username, password;

	public LoginInformation(String username, String password) {
		this.setUsername( username );
		this.setPassword( password );
	}

	@Override
	public String toString() {
		return username + " " + password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
