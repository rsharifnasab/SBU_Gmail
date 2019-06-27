package Network.ServerSide.SemiDataBase;

import BasicClasses.Profile;
import Network.ServerSide.Log.*;
import Network.ServerSide.*;

import java.io.*;
import java.util.concurrent.*;

public class ServerInitializer {
    private static ServerInitializer ourInstance = new ServerInitializer();
    private static final String address="src/DB/Data";

    public static ServerInitializer getInstance() {
        return ourInstance;
    }

  private ServerInitializer() {/* do nothing! */ }

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
