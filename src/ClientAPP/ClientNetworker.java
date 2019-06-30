package ClientAPP;

import BasicClasses.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientNetworker{

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
			System.out.println("invalid answer from server");
		} catch( IOException e){
			e.printStackTrace();
		}
		return recieved;
	}


}
