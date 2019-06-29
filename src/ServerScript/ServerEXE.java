package ServerScript;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import ServerScript.DB.*;

public class ServerEXE {

	private static boolean isServerUp = true;

  public static Map<String, Profile> profiles = null;


	public static void main(String[] args) {
		ServerInitializer.getInstance().initializeServer();

		ServerSocket serverSocket=null;

		try {
			serverSocket = new ServerSocket(Ports.CHAT_PORT);
			System.out.println("Server is Up!");

		} catch (IOException e) {
			System.out.println("Server Ports Are Full!");
			System.exit(12);
		}

		while ( Server.isIsServerUp() ){
			Socket currentuserSocket = null;
			try {
				System.out.println( "Waiting for a client..." );
				currentuserSocket = serverSocket.accept();
				//ChatHandler chatHandler=new ChatHandler(currentChatSocket);

				System.out.println("A Client Has Connected");

				new Thread( userHandler ).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
