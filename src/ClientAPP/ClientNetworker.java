package ClientAPP;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientNetworker{

	public static String serverAddress;
	public static final int PORT = 8888;

	private static boolean isConnected = false;
	public static Socket socket;
	public static ObjectInputStream socketIn;
	public static ObjectOutputStream socketOut;


	public static boolean isConnected(){
		return isConnected;
	}

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
			e.printStackTrace();
		}
		return false;
	}

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
	@SuppressWarnings("unchecked")
	public static Map<String,Object> serve(Map<String,Object> toSend){
		Map<String,Object> recieved = null;
		try{
			socketOut.writeObject(toSend);
			socketOut.flush();
			socketOut.reset();
			if (toSend.get("command")!= Command.SEND_MAIL){
				recieved = (Map<String,Object>) socketIn.readObject();
				return recieved;
			}
			return null;
		} catch (ClassNotFoundException e){
		} catch( IOException e){
			e.printStackTrace();
		}
		return recieved;
	}


}
