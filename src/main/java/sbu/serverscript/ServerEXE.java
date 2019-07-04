package sbu.serverscript;

import sbu.common.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


/**
		this is server main class that runs first
		it has EXE in its name because its executable
		it saves mail set and profiles map here
**/
public class ServerEXE {

	public static final int PORT = 2222;
	private static boolean isServerUp = true;

  public static Map<String, Profile> profiles = null;
	public static Set<Mail> mails = null;

	/**
		a simple method that return server up state
	**/
	public static boolean isServerUp(){
		return isServerUp;
	}

	/**
		 a method that gets mail and check if it has valid reciever or not
		 if it has not a valid reciever it send a mail from mailer daemon to mail sender
	**/
	public static void checkValidMail(Mail mail){
		String recieverName = mail.getReciever();
		if (recieverName.equals("mailerdaemon")) return; // loop!
		Profile recieverProf = profiles.get(recieverName);
		if (recieverProf != null) return; //its ok

		String message = "Dear user\nwe are afraid that tell you we can't handle your recent request\nthe address you requested is not valid in our servers\n";
		Mail answer = new Mail("mailerdaemon", mail.getSender() , "Mail Delivery Subsystem" , message);

		Map<String,Object> outcome =  new HashMap<>();
		outcome.put("mail",answer);
		outcome.put("command",Command.SEND_MAIL);
		API.sendMail(outcome);
		ServerEXE.mails.remove(mail); // delete invalid mail from server! //TODO
	}

	/**
		main method of server which initialize server db using server initializer
		it create server =socket and start to listen
		after every client is connected, it create a new client handler thread to manage it
	**/
	public static void main(String[] args) {
		DBManager.getInstance().initializeServer();

		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(PORT);

		} catch (IOException e) {
			System.exit(12);
		}

		while ( isServerUp() ){
			Socket currentUserSocket = null;
			try {
				currentUserSocket = serverSocket.accept();
				ClientHandler clientHandler=new ClientHandler(currentUserSocket);
				new Thread( clientHandler ).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
