package ClientAPP;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientNetworker{

	public static Profile profile;
	static String serverAddress;
	static final int PORT = 8888;

	private static boolean isConnected = false;
	public static Socket socket;
	public static ObjectInputStream socketIn;
	public static ObjectOutputStream socketOut;


	public static boolean isConnected(){
		return isConnected;
	}

	public static Boolean connectToServer(){
		System.out.println("trying to connect to server");
		if(socket != null) return false;
		try{
			socket = new Socket( serverAddress, PORT);
			System.out.println("socket ok");
			socketOut = new ObjectOutputStream( socket.getOutputStream() );
			socketIn = new ObjectInputStream( socket.getInputStream() );
			System.out.println("input output stream ok");
			System.out.println("conected to server!");
			isConnected = true;
			return true;

		}catch (ConnectException e){
			System.out.println("failed to connect to server");
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

			System.out.println("disconncted from server Sucesfully");
			isConnected = false;

			socket = null;
			socketIn = null;
			socketOut = null;

			return true;
		}
		catch( NullPointerException e){
			System.out.println("wasnt connect BTW");
		}
		catch( Exception e){
			e.printStackTrace();
		}
		return false;
	}


}