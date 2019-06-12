
package BasicClasses;

/*
Too hamoon safhe-e avval, vaghti taraf sa'y mikone login kone
maa ettelaa'aati ro ke taraf vaared karde, dar ghaaleb-e ye
LoginInformation mifrestim be Server, ke check konim bebinim
hamchin useri vojood daare yaa na!
 */

import java.io.Serializable;

public class LoginInformation implements Serializable {

//  Username daare va Password!
	private String username, password;

//	Constructor
	public LoginInformation(String username, String password) {
		this.setUsername( username );
		this.setPassword( password );
	}

//	toString!
	@Override
	public String toString() {
		return username + " " + password;
	}

//	Getters and Setters!

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
