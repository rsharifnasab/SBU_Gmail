package ServerScript;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

import ServerScript.DB.*;

/**
	it provide server srevices to client
	every method is stands for a command in BasicClasses.Command
	and you can have sheet sheet of each map contents there

	note that server  and client pass a map<String,object> from socket
	every map have a command value with "command" key
	with a switch case we can find out which method is needed
**/
public class API {

	private static Comparator<Mail> readCompare = (a,b) -> Boolean.compare(a.isUnRead(), b.isUnRead() );
	private static Comparator<Mail> timeCompare = (a,b) -> Long.compare(a.getTimeLong(), b.getTimeLong()) ;
	private static Comparator<Mail> mailCompare = readCompare.thenComparing(timeCompare);


	/**
		an serverside api to check if a username exists and client can create a profile with that usetrname or not
		we know that username must be unique
		it checks profiles map to check if profile eith that usrename exists or not
	**/
	@SuppressWarnings("unchecked")
	public static Map<String,Object> isUserNameExists(Map<String,Object> income){

		String username2check = (String) income.get("username");
		Profile profile = ServerEXE.profiles.get(username2check);
		Boolean exists = (profile != null);

		Map<String,Object> ans = new HashMap<>();
		ans.put("answer",exists);
		ans.put("command",Command.USERNAME_UNIQUE);

		return ans;
	}

	/**
		login api in server side
		it give username and passwrod from income map and check if the username Exists
		if the username doesnt exists it return null profile with the exists boolean : false
		if the username exists, the exists boolean is true
		then it use profile.authenticate to make sure that the username and password match ( authenticate could be changed later for improve security)
		if the username and Password math, we return profile (which is used as token)
		otherwise we return null as profile
	**/
	@SuppressWarnings("unchecked")
	public static Map<String,Object> login(Map<String,Object> income){

		String username = (String) income.get("username");
		String password = (String) income.get("password");

		Boolean isNullProfile = (ServerEXE.profiles.get(username) == null);
		Map<String,Object> ans = new HashMap<>();
		ans.put("command",Command.LOGIN);
		ans.put("exists",!isNullProfile);
		if(isNullProfile){
			return ans;
		}
		Profile profile = ServerEXE.profiles.get(username).authenticate(username, password);
		ans.put("answer",profile);

		if(profile != null){
			System.out.println(profile.getUserName() + " signin");
			System.out.println("time : "+Time.getTime());
		}
		return ans;
	}

	/**
		the simple signup api
		it give a profile from user and save it to database
		and it pudate the local data base
	**/
	@SuppressWarnings("unchecked")
	public static Map<String,Object> signUp(Map<String,Object> income){
		Profile newProfile = (Profile) income.get("profile");
		String username = newProfile.getUserName();
		ServerEXE.profiles.put(username,newProfile);
		DBUpdator.getInstance().updateDataBase(); // save to local file
		Map<String,Object> ans = new HashMap<>();
		ans.put("command",Command.SIGNUP);
		ans.put("answer",new Boolean(true));

		System.out.println(newProfile.getUserName() + " register" /* + TODO */); //add image address
		System.out.println("time : "+Time.getTime());
		System.out.println(newProfile.getUserName() + " signin");
		System.out.println("time : "+Time.getTime());

		return ans;
	}


	/**
		update profile method give a profile that is already exists in set
		it delete old profle and replace new profile
		it also save changes to DB
	**/
	@SuppressWarnings("unchecked")
	public static Map<String,Object> updateProfile(Map<String,Object> income){

		Profile newProfile = (Profile) income.get("profile");
		String username = newProfile.getUserName();
		ServerEXE.profiles.replace(username,newProfile);
		DBUpdator.getInstance().updateDataBase(); // save to local file

		Map<String,Object> ans = new HashMap<>();
		ans.put("command",Command.UPDATE_PROFILE);
		ans.put("answer",new Boolean(true));
		return ans;
	}

	/**
		the logout method in server side
		it doesnt do anything special :))
	**/
	@SuppressWarnings("unchecked")
	public static Map<String,Object> logout(Map<String,Object> income){
		Map<String,Object> ans = new HashMap<>();
		ans.put("command",Command.LOGOUT);
		ans.put("answer",new Boolean(true));
		return ans;
	}

	/**
		the api for sending mail
		it give a Mail object and add it to mails List and update loacal DB
		after a client want to its new mail, we check mails list so we send him all of his/her mails
	**/
	@SuppressWarnings("unchecked")
	public static Map<String,Object> sendMail(Map<String,Object> income){

		Mail mail = (Mail) income.get("mail");
		ServerEXE.mails.add(mail);
		DBUpdator.getInstance().updateDataBase(); // save to local file

		Map<String,Object> ans = new HashMap<>();
		ans.put("command",Command.SEND_MAIL);
		ans.put("answer",new Boolean(true));

		System.out.println(mail.getReciever() + " recieve");
		System.out.println("message: "+ mail.getSender() + " ");
		System.out.println("time : "+Time.getTime());

		ServerEXE.checkValidMail(mail);

		return ans;
	}

	/**
		the check mails api in clientside
		first of all we get profile of user as token so that we make sure he/she is authenticated
			in returning map we return several lists for inbox, sent and trash
		it will sort mails and then return them
	**/
	@SuppressWarnings("unchecked")
	public static Map<String,Object> checkMail(Map<String,Object> income){
		Profile profile = (Profile) income.get("profile");
		String username = profile.getUserName();

		Map<String,Object> ans = new HashMap<>();
		ans.put("command",Command.CHECK_MAIL);

		List<Mail> sent = ServerEXE
		.mails
		.stream()
		.filter(a-> a.getSender().equals(username))
		.sorted(mailCompare)
		.collect (Collectors.toList());

		List<Mail> inbox = ServerEXE
		.mails
		.stream()
		.filter(a-> a.getReciever().equals(username))
		.filter(a -> a.isTrashed() == false)
		.sorted(mailCompare)
		.collect (Collectors.toList());

		List<Mail> trash = ServerEXE
		.mails
		.stream()
		.filter(a-> a.getReciever().equals(username))
		.filter(a -> a.isTrashed())
		.sorted(mailCompare)
		.collect (Collectors.toList());

		ans.put("sent",sent);
		ans.put("inbox",inbox);
		ans.put("trash",trash);

		return ans;
	}

	/**
		a method that get a new mail and comapre to old mail
		and print logs based on which changes that new mail had
	**/
	private static void changeMailLogger(Mail newMail){
		List<Mail> possibleOptions = ServerEXE
		.mails
		.stream()
		.filter(a-> a.toString().equals(newMail.toString()))
		.collect (Collectors.toList());
		if(possibleOptions.size() <1) return;
		Mail oldMail = possibleOptions.get(0);
		String toPrint1 = newMail.getReciever() + " mark\n + message: " + newMail.getSubject() + " " + newMail.getSender() + " as ";
		String toPrint2 = "\ntime: " + newMail.getTimeString();
		if( (newMail.isUnRead()) != ( oldMail.isUnRead() ) )
			System.out.println(toPrint1 + "read" + toPrint2);

		toPrint1 = newMail.getReciever() + " removemsg\n + message: " + newMail.getSubject() + " " + newMail.getSender() + "\n";
		if(newMail.isTrashed() != oldMail.isTrashed())
			System.out.println(toPrint1 + toPrint2);
	}

	/**
		an api that get mail and update an old mail in server with new mail
		it can only change traashed status and read status
		it also save new db to file
	**/
	public static Map<String,Object> changeMail(Map<String,Object> income){
		Mail newMail = (Mail) income.get("mail");
		changeMailLogger(newMail);

		ServerEXE.mails.remove(newMail);
		ServerEXE.mails.add(newMail);

		DBUpdator.getInstance().updateDataBase(); // save to local file

		Map<String,Object> ans = new HashMap<>();
		ans.put("command",Command.CHANGE_MAIL);
		ans.put("answer",new Boolean(true));

		return ans;
	}

}
