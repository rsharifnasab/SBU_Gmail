package ClientAPP;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class API{
	public static boolean isUserNameExists(String username2check){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.USERNAME_UNIQUE);
		toSend.put("username",username2check);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		return (boolean) recieved.get("answer");
	}

	public static Profile login(String username, String password){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.LOGIN);
		toSend.put("username",username);
		toSend.put("password",password);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		if ( recieved.get("answer") == null ) return null;
		return (Profile) recieved.get("answer");
	}

	public static Boolean signUp(Profile profile){
		System.out.println("sending "+profile+ " to server");
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.SIGNUP);
		toSend.put("profile", profile);
		System.out.println(toSend);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		if ( recieved.get("answer") == null ) return null;
		return (Boolean) recieved.get("answer");
	}

	public static Boolean updateProfile(Profile profile){
		System.out.println("sending "+profile+ " to serve for update");
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.UPDATE_PROFILE);
		toSend.put("profile", profile);
		System.out.println(toSend);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		if ( recieved.get("answer") == null ) return false;
		return (Boolean) recieved.get("answer");
	}

}
