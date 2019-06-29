package ServerScript.DB;

import ServerScript.*;

import java.io.*;

public class DBUpdator {
    private static DBUpdator ourInstance = new DBUpdator();
    private static final String address= "src/ServerScript/DB/Data";
    public static DBUpdator getInstance() {
        return ourInstance;
    }

    private DBUpdator() {/*nothing*/ }

    public synchronized void updateDataBase(){
      try {
          FileOutputStream fout = new FileOutputStream(address);
          ObjectOutputStream objToFile = new ObjectOutputStream(fout);
          objToFile.writeObject(ServerEXE.profiles);
          objToFile.close();
          fout.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

}
