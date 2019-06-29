package ServerScript.DB;

import BasicClasses.Profile;

import Network.ServerSide.*;

import java.io.*;
import java.util.concurrent.*;

public class ServerInitializer {
    private static ServerInitializer ourInstance = new ServerInitializer();
    private static final String address="src/ServerScript/DB/Data";

    public static ServerInitializer getInstance() {
        return ourInstance;
    }

  private ServerInitializer() {/* do nothing! */ }

  @SuppressWarnings("unchecked")
  public void initializeServer(){
    try {
      FileInputStream fin=new FileInputStream(address);
      ObjectInputStream inFromFile=new ObjectInputStream(fin);
      Server.profiles = new ConcurrentHashMap<>( (ConcurrentHashMap<String, Profile>) inFromFile.readObject());
      inFromFile.close();
      fin.close();
    }
    catch (Exception e){
        if (e instanceof EOFException){
          System.out.println("data base is empty");
          Server.profiles = new ConcurrentHashMap<>();
        }
        else e.printStackTrace();
    }
  }
}
