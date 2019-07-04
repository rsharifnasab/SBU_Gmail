package sbu.serverscript;

import sbu.common.*;
import sbu.serverscript.*;

import java.io.*;
import java.util.concurrent.*;

/**
a singleton class that intialize sever database and load from file
**/
public class DBManager {

  public static final String FILE_PREFIX = "./db/";
  public static final String PROFILES_FILE = FILE_PREFIX + "ProfilesDB";
  public static final String MAILS_FILE =  FILE_PREFIX + "MailDB";


  private static DBManager ourInstance = new DBManager();

  /**
  only way to se use this class is using this method and get inly instance of this class
  **/
  public static DBManager getInstance() {
    return ourInstance;
  }


  /**
  private constructor of this class to prevent compiler of creating public constructor
  so that nobody can create instance of this class
  **/
private DBManager() {/* do nothing! */ }

/**
  it read local data base files and load them to server profiles and mails list
**/
@SuppressWarnings("unchecked")
public synchronized void initializeServer(){
  try {
    FileInputStream fin=new FileInputStream(DBManager.PROFILES_FILE);
    ObjectInputStream inFromFile=new ObjectInputStream(fin);
    ServerEXE.profiles = new ConcurrentHashMap<>( (ConcurrentHashMap<String, Profile>) inFromFile.readObject());
    inFromFile.close();
    fin.close();
  }
  catch(EOFException | StreamCorruptedException e){
    ServerEXE.profiles = new ConcurrentHashMap<>();
  }catch (Exception e){
    e.printStackTrace();
  }

  try {
    FileInputStream fin = new FileInputStream(DBManager.MAILS_FILE);
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

/**
  it is synchronized to prevent multithread problems
  it save latests change of profiles and mails to file
**/
public synchronized void updateDataBase(){
  try {
      FileOutputStream fout = new FileOutputStream(PROFILES_FILE);
      ObjectOutputStream objToFile = new ObjectOutputStream(fout);
      objToFile.writeObject(ServerEXE.profiles); //writing profiles
      objToFile.close();
      fout.close();

      fout = new FileOutputStream(MAILS_FILE);
      objToFile = new ObjectOutputStream(fout);
      objToFile.writeObject(ServerEXE.mails); // writing mails
      objToFile.close();
      fout.close();

  } catch (IOException e) {
    e.printStackTrace();
  }
}




}
