package ServerScript.DB;

import BasicClasses.*;
import ServerScript.*;

import java.io.*;
import java.util.concurrent.*;

public class ServerInitializer {
    private static ServerInitializer ourInstance = new ServerInitializer();

    public static ServerInitializer getInstance() {
        return ourInstance;
    }

    private ServerInitializer() {/* do nothing! */ }

  @SuppressWarnings("unchecked")
  public void initializeServer(){
    try {
      FileInputStream fin=new FileInputStream(DBUpdator.PROFILES_FILE);
      ObjectInputStream inFromFile=new ObjectInputStream(fin);
      ServerEXE.profiles = new ConcurrentHashMap<>( (ConcurrentHashMap<String, Profile>) inFromFile.readObject());
      inFromFile.close();
      fin.close();
    }
    catch(EOFException e){
        ServerEXE.profiles = new ConcurrentHashMap<>();
    }catch (Exception e){
     e.printStackTrace();
    }

    try {
      FileInputStream fin = new FileInputStream(DBUpdator.MAILS_FILE);
      ObjectInputStream inFromFile = new ObjectInputStream(fin);
      ServerEXE.mails = new ConcurrentSkipListSet<>( (ConcurrentSkipListSet<Mail>) inFromFile.readObject());
      inFromFile.close();
      fin.close();
    }
    catch(EOFException | StreamCorruptedException e){
        ServerEXE.mails = new ConcurrentSkipListSet<>();
    }catch (Exception e){
     e.printStackTrace();
    }

  }
}
