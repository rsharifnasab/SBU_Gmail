package ClientAPP;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class API{
	public static boolean isUserNameExists(String username2check){
		Map<String,Object> toSend = new ConcurrentHashMap<>();
		toSend.put("command", Command.USERNAME_UNIQUE);
		toSend.put("username",username2check);
		Map<String,Object> recieved = ClientNetworker.serve(toSend);
		return (boolean) recieved.get("answer");
	}

}
