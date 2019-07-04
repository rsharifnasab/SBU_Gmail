package sbu.clientAPP;

import sbu.common.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
	netowrk manage part in clientside program
	it connect and disconnct from server and also save socket and onput output stream
**/
public class ClientNetworker{

	public static String serverAddress;
	public static final int PORT = 2222;

	private static boolean isConnected = false;
	public static Socket socket;
	public static ObjectInputStream socketIn;
	public static ObjectOutputStream socketOut;

	/**
		return status of connecting to server
		it prevousely saved to a boolean
	**/
	public static boolean isConnected(){
		return isConnected;
	}

	/**
		a method to connect to server
		it check it socket is null that shows we are not connected to any server
		after connecting successfully it make connected boolean as true
	**/
	public static Boolean connectToServer(){
		if(socket != null) return false;
		try{
			socket = new Socket( serverAddress, PORT);
			socketOut = new ObjectOutputStream( socket.getOutputStream() );
			socketIn = new ObjectInputStream( socket.getInputStream() );
			isConnected = true;
			return true;

		}catch (ConnectException e){
		}
		catch (IOException e) {
		}
		return false;
	}


	/**
		 a method that disconnect from server and make socket null
		 it also make isconnected boolean as false
	**/
	public static Boolean disconnectFromServer(){
		try{
			socketIn.close();
			socketOut.close();
			socket.close();
			isConnected = false;

			socket = null;
			socketIn = null;
			socketOut = null;

			return true;
		}
		catch( NullPointerException e){
		}
		catch( Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/**
		main method of sending and recieving to server
		if named serve because it give command and send it to server and return the response
		as i told, it send a map<String,Object> and recieve same map too
		it use socletout to send and socketin top recieve
		it flush the socketout to make sure that buffering dont make delay for us
	**/
	@SuppressWarnings("unchecked")
	public static Map<String,Object> serve(Map<String,Object> toSend){
		Map<String,Object> recieved = null;
		try{
			socketOut.writeObject(toSend);
			socketOut.flush();
			socketOut.reset();
			recieved = (Map<String,Object>) socketIn.readObject();
			return recieved;

		} catch (ClassNotFoundException e){
		} catch( IOException e){
			e.printStackTrace();
		}
		return recieved;
	}

}
