package ServerScript;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

import ServerScript.DB.*;

public class API {
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

	@SuppressWarnings("unchecked")
	public static Map<String,Object> logout(Map<String,Object> income){
		Map<String,Object> ans = new HashMap<>();
		ans.put("command",Command.LOGOUT);
		ans.put("answer",new Boolean(true));
		return ans;
	}

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
		.filter(a -> a.isTrashed() == false)
		.collect (Collectors.toList());

		List<Mail> inbox = ServerEXE
		.mails
		.stream()
		.filter(a-> a.getReciever().equals(username))
		.filter(a -> a.isTrashed() == false)
		.collect (Collectors.toList());

		List<Mail> trash = ServerEXE
		.mails
		.stream()
		.filter(a-> a.getReciever().equals(username) || a.getSender().equals(username))
		.filter(a -> a.isTrashed())
		.collect (Collectors.toList());

		ans.put("sent",sent);
		ans.put("inbox",inbox);
		ans.put("trash",trash);

		return ans;
	}

	public static Map<String,Object> changeMail(Map<String,Object> income){
		Mail newMail = (Mail) income.get("mail");
		ServerEXE.mails.remove(newMail);
		ServerEXE.mails.add(newMail);

		DBUpdator.getInstance().updateDataBase(); // save to local file

		Map<String,Object> ans = new HashMap<>();
		ans.put("command",Command.CHANGE_MAIL);
		ans.put("answer",new Boolean(true));
		
		return ans;
	}







}
