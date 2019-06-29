package ServerScript;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ClientHandler implements Runnable {

	private Socket userSocket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;

	public ClientHandler(Socket socket){
		try{
			userSocket = socket;
			ObjectInputStream socketIn = new ObjectInputStream (userSocket.getInputStream() );
			ObjectOutputStream socketOut = new ObjectOutputStream (userSocket.getOutputStream() );
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void run(){

		while(true){
			System.out.println("waiting for object from user");
			try{
				Thread.sleep(1900);
				Object o = socketIn.readObject();
				System.out.println(o);
				socketOut.writeObject(new String("bia"));
				socketOut.flush();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			break;
		}
	}



}
