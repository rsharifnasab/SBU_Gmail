package ServerScript.DB;

import BasicClasses.*;
import ServerScript.*;

import java.io.*;
import java.util.concurrent.*;

/**
a singleton class that intialize sever database and load from file
**/
public class ServerInitializer {
  private static ServerInitializer ourInstance = new ServerInitializer();

  /**
  only way to se use this class is using this method and get inly instance of this class
  **/
  public static ServerInitializer getInstance() {
    return ourInstance;
  }


  /**
  private constructor of this class to prevent compiler of creating public constructor
  so that nobody can create instance of this class
  **/
private ServerInitializer() {/* do nothing! */ }

/**
  only useful method of this Class
  it read local data base files and load them to server profiles and mails list
**/
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
  System.out.println(ServerEXE.mails.size());
  for(Mail mail : ServerEXE.mails){
    System.out.println(mail);
  }
}
}
