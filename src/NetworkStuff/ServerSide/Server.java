
package NetworkStuff.ServerSide;

import Enums.Ports;
import Game.Match;
import Game.Profile;
import NetworkStuff.ServerSide.Handlers.ChatHandler;
import NetworkStuff.ServerSide.Handlers.GameHandler;
import NetworkStuff.ServerSide.Handlers.JoinGameHandler;
import NetworkStuff.ServerSide.Handlers.UserHandler;
import NetworkStuff.ServerSide.Log.ServerLogWriter;
import NetworkStuff.ServerSide.SemiDataBase.ServerInitializer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
Server :))
 */

public class Server {

//	Is server running?
	private static boolean isServerUp = true;

//	ProfileHaaE ke ta alaan saakhte shodan ro injaa zakhire mikonim!
//	public static List<Profile> profiles = Collections.synchronizedList( new ArrayList<Profile>() );
	public static Map<String, Profile> profiles = new ConcurrentHashMap<String, Profile>();
// challenge haaE ke ta alan saakhte shodan
	public static Map<Match,Profile> challenges=new ConcurrentHashMap<>();
//	Ye map dREm az profileHaa be userHandlerHaa
	public static Map<Profile, UserHandler> userHandlers = new ConcurrentHashMap<Profile, UserHandler>();
	public static Map<UserHandler, JoinGameHandler> joinGameHandlers = new ConcurrentHashMap<>();
	public static Map<UserHandler, ChatHandler> chatHandlers = new ConcurrentHashMap<>();
	public static Map<UserHandler, GameHandler> gameHandlers = new ConcurrentHashMap<>();

	public static void main(String[] args) {
		ServerInitializer.getInstance().initializeServer();



		/*Profile p1=new Profile();
		p1.setUserName("a");
		p1.setName("a");
		p1.setPassword("a");
		profiles.put(p1.getUserName(),p1);

		Profile p2=new Profile();
		p2.setUserName("q");
		p2.setName("q");
		p2.setPassword("q");
		profiles.put(p2.getUserName(),p2);

		Profile p3=new Profile();
		p3.setUserName("r");
		p3.setName("r");
		p3.setPassword("r");
		profiles.put(p3.getUserName(),p3);*/

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
				//System.out.println( "Waiting for a client..." );
				currentuserSocket = userSocket.accept();
				UserHandler userHandler = new UserHandler( currentuserSocket );
				//System.out.println( "waiting for the clients chatsocket" );
				currentChatSocket=chatSocket.accept();
				ChatHandler chatHandler=new ChatHandler(currentChatSocket);
				chatHandlers.put(userHandler,chatHandler);
				//System.out.println("waiting for the clients joinsocket");
				currentJoinSocket=joinSocket.accept();
				JoinGameHandler joinGameHandler=new JoinGameHandler(currentJoinSocket);
				joinGameHandlers.put(userHandler,joinGameHandler);
				//System.out.println("waiting for the clients gamesocket");
				currentGameSocket=gameSocket.accept();
				GameHandler gameHandler=new GameHandler(currentGameSocket);
				gameHandlers.put( userHandler, gameHandler );
				ServerLogWriter.getInstance().writeLog("A Client Has Connected");
				//System.out.println("got all the sockets nigga");


				new Thread( userHandler ).start();
				new Thread(chatHandler).start();
				new Thread(joinGameHandler).start();
				new Thread(gameHandler).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

/*

//	Behesh ye chiZ az jens-e LoginInformation pass midim, behemoon mige ke aayaa useri baa in moshakhasaat vojood daare yaa na!
	public static boolean isLoginInformationValid( LoginInformation loginInformation ) {
		for ( Profile currentProfile : Server.profiles ) {
			if (currentProfile.getUserName().equals(loginInformation.getUsername()))
				if (currentProfile.getPassword().equals(loginInformation.getPassword()))
					return true;
		}
		return false;
	}
*/

//	Getters and Setters

	public static boolean isIsServerUp() {
		return isServerUp;
	}

	public static void setIsServerUp(boolean isServerUp) {
		Server.isServerUp = isServerUp;
	}

}
