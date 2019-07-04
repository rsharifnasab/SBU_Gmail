package sbu.serverscript.db;

import sbu.serverscript.*;
import sbu.common.*;

import java.io.*;

/**
  a singleton class that update the file data base and seve mail lists and profile lists to that
  it has a private constructor in order to be a singleton
  it saves path of local databes files in two final stirngs
**/
public class DBUpdator {
    private static DBUpdator ourInstance = new DBUpdator();
    
    public static final String FILE_PREFIX = "./db/";
    public static final String PROFILES_FILE = FILE_PREFIX + "ProfilesDB";
    public static final String MAILS_FILE =  FILE_PREFIX + "MailDB";

    /**
      only way of using this class,
      it return our instance for let other clases use this
    **/
    public static DBUpdator getInstance() {
        return ourInstance;
    }

    /**
      private constructor of this class to forbids compiler make a public constructor
      and as a result nobody out of this class can create instance of this class
    **/
    private DBUpdator() {/*nothing*/ }

    /**
      only useful method of this class is pudate data base
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
