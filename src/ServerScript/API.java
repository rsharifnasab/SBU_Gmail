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

		Map<String,Object> ans = new ConcurrentHashMap<>();
		ans.put("answer",exists);
		ans.put("command",Command.USERNAME_UNIQUE);

		return ans;
	}
}
