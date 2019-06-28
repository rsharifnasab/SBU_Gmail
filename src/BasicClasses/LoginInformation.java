
package BasicClasses;

import java.io.*;

public class LoginInformation implements Serializable {


	private String username, password;

//	Constructor
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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
