package ServerScript;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import ServerScript.DB.*;

public class ServerEXE {

	public static final int PORT = 8888;
	private static boolean isServerUp = true;

  public static Map<String, Profile> profiles = null;

	public static boolean isServerUp(){
		return isServerUp;
	}

	public static void main(String[] args) {
		ServerInitializer.getInstance().initializeServer();

		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server is Up!");

		} catch (IOException e) {
			System.out.println("server cant open server socket!");
			System.exit(12);
		}

		while ( isServerUp() ){
			Socket currentUserSocket = null;
			try {
				System.out.println( "Waiting for a client..." );
				currentUserSocket = serverSocket.accept();
				ObjectInputStream socketIn = new ObjectInputStream (currentUserSocket.getInputStream());
				ObjectOutputStream socketOut = new ObjectOutputStream (currentUserSocket.getOutputStream());
				//ChatHandler chatHandler=new ChatHandler(currentChatSocket);

				System.out.println("A Client Has Connected");

				//new Thread( userHandler ).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
