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
	public Boolean clientOnline = true;
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

		while(clientOnline){
			Map<String,Object> income = null;

			try{
				income = (Map<String,Object>) socketIn.readObject();
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
					case LOGOUT:
						answer = API.logout(income);
						clientOnline = false;
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
				System.out.println("IOExepctio happpenddd");
				e.printStackTrace();
			}

		}
		try{
			socketIn.close();
			socketOut.close();
			userSocket.close();
		}catch(IOException e){
			System.out.println("error in closing client socket");
		}

	}



}
