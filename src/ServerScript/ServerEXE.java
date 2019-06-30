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
	public static Set<Mail> mails = null;

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
				currentUserSocket = serverSocket.accept();
				ClientHandler clientHandler=new ClientHandler(currentUserSocket);
				new Thread( clientHandler ).start();

				System.out.println("A Client Has Connected");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
