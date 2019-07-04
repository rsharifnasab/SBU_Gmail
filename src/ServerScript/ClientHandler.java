package ServerScript;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
	client handler class that handle every client with uts new instace
	when a client connected new instance (and new thread) of this class will be created
	it saves socket and onput output stream in itself
	it needs socket for instansiaction
**/
public class ClientHandler implements Runnable {

	private Socket userSocket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	public Boolean clientOnline = true;

	/**
		only constructor of this class
		it give socket and create object input output stream and save them
	**/
	public ClientHandler(Socket socket){
		try{
			userSocket = socket;
			this.socketIn = new ObjectInputStream (userSocket.getInputStream() );
			this.socketOut = new ObjectOutputStream (userSocket.getOutputStream() );
		} catch(IOException e){
			e.printStackTrace();
		}
	}


	/**
		main method if this class that handle eveything
		it is run method that runs in parallel when we call .start()
		it give a map from socketint and read its command
		and call api based on which command it should call
		it use a swtich case that wich api should be called
		after calling api it send answer to socketOut
		if command is logout, it break the while and close the sockets
	**/
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
					case SEND_MAIL:
						answer = API.sendMail(income);
						break;
					case CHECK_MAIL:
						answer = API.checkMail(income);
						break;
					case CHANGE_MAIL:
						answer = API.changeMail(income);
						break;

				}
				socketOut.writeObject(answer);
				socketOut.flush();
			}
			catch(ClassCastException | ClassNotFoundException e){
			}
			catch(EOFException e){
				break;
			}
			catch(IOException e){
				break;
			}

		}
		// after loggin out!
		try{
			socketIn.close();
			socketOut.close();
			userSocket.close();
		}catch(IOException e){}

	}
}
