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
      System.out.println("server initialized with profiles:\n" + ServerEXE.profiles);
      inFromFile.close();
      fin.close();



    }
    catch(EOFException e){
        System.out.println("profile list is empty");
        ServerEXE.profiles = new ConcurrentHashMap<>();
    }catch (Exception e){
     e.printStackTrace();
    }

    try {
      FileInputStream fin = new FileInputStream(DBUpdator.MAILS_FILE);
      ObjectInputStream inFromFile = new ObjectInputStream(fin);
      ServerEXE.mails = new ConcurrentSkipListSet<>( (ConcurrentSkipListSet<Mail>) inFromFile.readObject());
      System.out.println("server initialized with mails:\n" + ServerEXE.mails);
      inFromFile.close();
      fin.close();
    }
    catch(EOFException | StreamCorruptedException e){
        System.out.println("mail list is empty");
        ServerEXE.mails = new ConcurrentSkipListSet<>();
    }catch (Exception e){
     e.printStackTrace();
    }

  }
}
