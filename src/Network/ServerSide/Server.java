
package Network.ServerSide;

import Enums.Ports;
import BasicClasses.*;
import Network.ServerSide.*;
import Network.ServerSide.Handlers.*;


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import Network.ServerSide.DBHandler.*;

public class Server {

//	Is server running?
	private static boolean isServerUp = true;

//	ProfileHaaE ke ta alaan saakhte shodan ro injaa zakhire mikonim!

	public static Map<String, Profile> profiles = new ConcurrentHashMap<String, Profile>();
// challenge haaE ke ta alan saakhte shodan
//	Ye map dREm az profileHaa be userHandlerHaa
	public static Map<Profile, UserHandler> userHandlers = new ConcurrentHashMap<>();
	public static Map<UserHandler, ChatHandler> chatHandlers = new ConcurrentHashMap<>(); //TODO


	public static void main(String[] args) {
		ServerInitializer.getInstance().initializeServer();

		ServerSocket userSocket = null;
		ServerSocket chatSocket=null;


		try {
			userSocket = new ServerSocket(Ports.USER_PORT );
			chatSocket = new ServerSocket(Ports.CHAT_PORT);
			ServerLogWriter.getInstance().writeLog("Server is Up!");

		} catch (IOException e) {
			ServerLogWriter.getInstance().writeLog("Server Ports Are Full!");
			System.exit(12);
		}

		while ( Server.isIsServerUp() ){
			Socket currentuserSocket = null;
			Socket currentChatSocket=null;
			try {
				System.out.println( "Waiting for a client..." );
				currentuserSocket = userSocket.accept();
				UserHandler userHandler = new UserHandler( currentuserSocket );
				System.out.println( "waiting for the clients chatsocket" );
				currentChatSocket=chatSocket.accept();
				ChatHandler chatHandler=new ChatHandler(currentChatSocket);
				chatHandlers.put(userHandler,chatHandler);
				ServerLogWriter.getInstance().writeLog("A Client Has Connected");
				System.out.println("got all the sockets nigga");


				new Thread( userHandler ).start();
				new Thread( chatHandler ).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


//	Getters and Setters

	public static boolean isIsServerUp() {
		return isServerUp;
	}

	public static void setIsServerUp(boolean isServerUp) {
		Server.isServerUp = isServerUp;
	}

}
