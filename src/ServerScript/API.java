package ServerScript;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

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
		System.out.println("ok user exists, authenticating");
		Profile profile = ServerEXE.profiles.get(username).authenticate(username, password);
		System.out.println(" profile is : " + profile);
		ans.put("answer",profile);
		System.out.println("ans is :" + ans);
		return ans;
	}

	@SuppressWarnings("unchecked")
	public static Map<String,Object> signUp(Map<String,Object> income){
		System.out.println("get signup request from user");
		Profile newProfile = (Profile) income.get("profile");
		String username = newProfile.getUserName();
		ServerEXE.profiles.put(username,newProfile);
		DBUpdator.getInstance().updateDataBase(); // sync local file
		Map<String,Object> ans = new HashMap<>();
		ans.put("command",Command.SIGNUP);
		ans.put("answer",new Boolean(true));
		return ans;
	}

	@SuppressWarnings("unchecked")
	public static Map<String,Object> updateProfile(Map<String,Object> income){
		System.out.println("get update profile request from user");
		Profile newProfile = (Profile) income.get("profile");
		String username = newProfile.getUserName();
		ServerEXE.profiles.remove(username); //TODO ?
		ServerEXE.profiles.put(username,newProfile);
		DBUpdator.getInstance().updateDataBase(); // sync local file
		Map<String,Object> ans = new HashMap<>();
		ans.put("command",Command.UPDATE_PROFILE);
		ans.put("answer",new Boolean(true));
		return ans;
	}



}
