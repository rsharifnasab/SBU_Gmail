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

			try{
				income = (Map<String,Object>) socketIn.readObject();
				System.out.println("server got this from user:\n" + income);
				Map<String,Object> answer = null;
				Command command = (Command) income.get("command");
				switch(command){
					case USERNAME_UNIQUE:
						answer = API.isUserNameExists(income);
						break;
					case LOGIN:
						answer = API.login(income);
						break;
					case SIGNUP:
						answer = API.signUp(income);
						break;
					case UPDATE_PROFILE:
						answer = API.updateProfile(income);
						break;


				}
				socketOut.writeObject(answer);
				socketOut.flush();
			}
			catch(ClassCastException | ClassNotFoundException e){
				System.out.println("invalid request from client");
			}
			catch(EOFException e){
				System.out.println("a client has disconncted");
				break;
			}
			catch(IOException e){
				System.out.println("ioexepctio happpenddd");
				e.printStackTrace();
			}

		}
	}



}
