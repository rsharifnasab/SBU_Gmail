package ServerScript.DB;

import ServerScript.*;

import java.io.*;

public class DBUpdator {
    private static DBUpdator ourInstance = new DBUpdator();
    public static final String PROFILES_FILE = "src/ServerScript/DB/ProfilesDB";
    public static final String MAILS_FILE = "src/ServerScript/DB/MailDB";
    public static DBUpdator getInstance() {
        return ourInstance;
    }

    private DBUpdator() {/*nothing*/ }

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
