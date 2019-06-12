package NetworkStuff.ServerSide.SemiDataBase;

import Game.Profile;
import NetworkStuff.ServerSide.Log.ServerLogWriter;
import NetworkStuff.ServerSide.Server;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ConcurrentHashMap;

public class ServerInitializer {
    private static ServerInitializer ourInstance = new ServerInitializer();
    private static final String address="src/NetworkShit/ServerSide/SemiDataBase/Data";

    public static ServerInitializer getInstance() {
        return ourInstance;
    }

    private ServerInitializer() {
    }
    public void initializeServer(){
        try {
            FileInputStream fin=new FileInputStream(address);
            ObjectInputStream inFromFile=new ObjectInputStream(fin);
            Server.profiles=new ConcurrentHashMap<>((ConcurrentHashMap<String, Profile>)inFromFile.readObject());
            inFromFile.close();
            fin.close();
        }
        catch (IOException ioe){
            if (ioe instanceof EOFException)
                ServerLogWriter.getInstance().writeLog("data base is empty");
            else
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
            System.out.println("ertehwth");
        }
    }
}
