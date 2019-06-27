
package NetworkStuff.ServerSide;

import Enums.Ports;
import BasicClasses.*;
import NetworkStuff.ServerSide.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class Server {

//	Is server running?
	private static boolean isServerUp = true;

//	ProfileHaaE ke ta alaan saakhte shodan ro injaa zakhire mikonim!

	public static Map<String, Profile> profiles = new ConcurrentHashMap<String, Profile>();
// challenge haaE ke ta alan saakhte shodan
//	Ye map dREm az profileHaa be userHandlerHaa
	public static Map<Profile, UserHandler> userHandlers = new ConcurrentHashMap<Profile, UserHandler>();
	public static Map<UserHandler, ChatHandler> chatHandlers = new ConcurrentHashMap<>();


	public static void main(String[] args) {
		ServerInitializer.getInstance().initializeServer();

		ServerSocket userSocket = null;
		ServerSocket chatSocket=null;
		ServerSocket joinSocket=null;
		ServerSocket gameSocket=null;
		try {
			userSocket = new ServerSocket(Ports.USER_PORT );
			chatSocket=new ServerSocket(Ports.CHAT_PORT);
			joinSocket=new ServerSocket(Ports.JOINGAME_PORT);
			gameSocket=new ServerSocket(Ports.GAME_PORT);
			ServerLogWriter.getInstance().writeLog("Server is Up!");
		} catch (IOException e) {
			ServerLogWriter.getInstance().writeLog("Server Ports Are Full!");
			System.exit(12);
		}

		while ( Server.isIsServerUp() ){
			Socket currentuserSocket = null;
			Socket currentChatSocket=null;
			Socket currentJoinSocket=null;
			Socket currentGameSocket=null;
			try {
				System.out.println( "Waiting for a client..." );
				currentuserSocket = userSocket.accept();
				UserHandler userHandler = new UserHandler( currentuserSocket );
				System.out.println( "waiting for the clients chatsocket" );
				currentChatSocket=chatSocket.accept();
				ChatHandler chatHandler=new ChatHandler(currentChatSocket);
				chatHandlers.put(userHandler,chatHandler);
				System.out.println("waiting for the clients joinsocket");
				currentJoinSocket=joinSocket.accept();
				JoinGameHandler joinGameHandler=new JoinGameHandler(currentJoinSocket);
				joinGameHandlers.put(userHandler,joinGameHandler);
				System.out.println("waiting for the clients gamesocket");
				currentGameSocket=gameSocket.accept();
				GameHandler gameHandler=new GameHandler(currentGameSocket);
				gameHandlers.put( userHandler, gameHandler );
				ServerLogWriter.getInstance().writeLog("A Client Has Connected");
				System.out.println("got all the sockets nigga");


				new Thread( userHandler ).start();
				new Thread(chatHandler).start();
				new Thread(joinGameHandler).start();
				new Thread(gameHandler).start();

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
