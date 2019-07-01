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
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.SIGNUP);
		toSend.put("profile", profile);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		if ( recieved.get("answer") == null ) return null;
		return (Boolean) recieved.get("answer");
	}

	public static Boolean updateProfile(Profile profile){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.UPDATE_PROFILE);
		toSend.put("profile", profile);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		if ( recieved.get("answer") == null ) return false;
		return (Boolean) recieved.get("answer");
	}

	public static Boolean logout(){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.LOGOUT);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		if ( recieved.get("answer") == null ) return false;
		return (Boolean) recieved.get("answer");
	}

	public static void sendMail(Mail mail){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.SEND_MAIL);
		toSend.put("mail",mail);
		ClientEXE.outbox.add(mail); //TODO
		ClientNetworker.serve(toSend);
	}

	public static Map<String,Object> checkMail(){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.CHECK_MAIL);
		toSend.put("profile",ClientEXE.getProfile());
		Map<String,Object> recieved = ClientNetworker.serve(toSend);

		return recieved;
	}

	@SuppressWarnings("unchecked")
	public static List<Mail> getInbox(){
		Map<String,Object> all = checkMail();
		return (List<Mail>) all.get("inbox");
	}
	@SuppressWarnings("unchecked")
	public static List<Mail> getSent(){
		Map<String,Object> all = checkMail();
		return (List<Mail>) all.get("sent");
	}

}
