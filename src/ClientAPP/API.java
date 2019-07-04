package ClientAPP;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
	client side of api methods
	all of this methods are util methods
	they give something and send it to server and return the Response
	they use map<String,Object> for sending and recieving from Server
	all of maps have a command key with Command... valud to specify which api from server should be called
**/
public class API{

	/**
		give a username and check if it is exists in profile or not
		it check it from same api in server
	**/
	public static boolean isUserNameExists(String username2check){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.USERNAME_UNIQUE);
		toSend.put("username",username2check);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		return (boolean) recieved.get("answer");
	}

	/**
		it provides login for the user
		it give username and password from input and send it to Server
		the response have an exists boolean that shows the username exists or Not
		byw if the username and password matches, it return that user profile, which is used as token in next stages of program
		if username and passwrod doesnt match, it return null
	**/
	public static Profile login(String username, String password){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.LOGIN);
		toSend.put("username",username);
		toSend.put("password",password);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		if ( recieved.get("answer") == null ) return null;
		return (Profile) recieved.get("answer");
	}


	/**
		this api is used for signup
		if gets the profile which is made from signup page and save it in server
	**/
	public static Boolean signUp(Profile profile){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.SIGNUP);
		toSend.put("profile", profile);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		if ( recieved.get("answer") == null ) return null;
		return (Boolean) recieved.get("answer");
	}

	/**
		it updates user profile
		if give new profile from user and send it to server for replacing old profile
		it used for changing Profile
		of course with this method (or any method) you cant change username
	**/
	public static Boolean updateProfile(Profile profile){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.UPDATE_PROFILE);
		toSend.put("profile", profile);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		if ( recieved.get("answer") == null ) return false;
		return (Boolean) recieved.get("answer");
	}

	/**
		it tell to server that i want to quit
		server will close all connections after recieving this
	**/
	public static Boolean logout(){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.LOGOUT);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		if ( recieved.get("answer") == null ) return false;
		return (Boolean) recieved.get("answer");
	}

	/**
		this method help you to send mail
		it give a Mail that have all fields such as sender and reciever
		it save that mail to server
		if reciever username is not valid it wil send a mail from mailer daemon to you
	**/
	public static void sendMail(Mail mail){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.SEND_MAIL);
		toSend.put("mail",mail);
	//	ClientEXE.outbox.add(mail); //TODO
		ClientNetworker.serve(toSend);
	}

	/**
		it will check for you new mail;
		it will send you current profile as a token to help server authorize you
		as a result it send you a map of string , object which has inbox and send and trash ,..
	**/
	public static Map<String,Object> checkMail(){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.CHECK_MAIL);
		toSend.put("profile",ClientEXE.getProfile());
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		return recieved;
	}

	/**
		it updates a mail status
		you can change trash and read status
		you cant change subhect and text and sender and reciever because they are final
	**/
	public static boolean changeMail(Mail mail){
		Map<String,Object> toSend = new HashMap<>();
		toSend.put("command", Command.CHANGE_MAIL);
		toSend.put("mail",mail);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		return (boolean) recieved.get("answer");
	}

	/**
		this is not a pure api
		it use original checkmail for this`
		this return inbox field as a list
	**/
	@SuppressWarnings("unchecked")
	public static List<Mail> getInbox(){
		Map<String,Object> all = checkMail();
		return (List<Mail>) all.get("inbox");
	}

	/**
		this is not a pure api
		it use original checkmail for this`
		this return sent field as a list
	**/
	@SuppressWarnings("unchecked")
	public static List<Mail> getSent(){
		Map<String,Object> all = checkMail();
		return (List<Mail>) all.get("sent");
	}

	/**
		this is not a pure api
		it use original checkmail for this`
		this return trash field as a list
	**/
	@SuppressWarnings("unchecked")
	public static List<Mail> getTrash(){
		Map<String,Object> all = checkMail();
		return (List<Mail>) all.get("trash");
	}

}
