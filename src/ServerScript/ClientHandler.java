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
			this.socketIn = new ObjectInputStream (userSocket.getInputStream() );
			this.socketOut = new ObjectOutputStream (userSocket.getOutputStream() );
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void run(){

		while(true){
			Map<String,Object> income = null;

			System.out.println("waiting for object from user");
			try{
				income = (Map<String,Object>) socketIn.readObject();
				System.out.println("i recieved : " + income);
				socketOut.writeObject("recieved");
				socketOut.flush();
			}
			catch(ClassCastException | ClassNotFoundException e){
				System.out.println("invalid request from client");
			}
			catch(EOFException e){
				System.out.println("a client has disconncted");
			}
			catch(IOException e){
				e.printStackTrace();
			}
			break;
		}
	}



}
