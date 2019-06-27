package Network.ServerSide.DBHandler;

import Network.ServerSide.Server;

import java.io.*;

public class DDUpdator {
    private static DBUpdator ourInstance = new DBUpdator();
    private static final String address="src/DB/Data";
    public static DataBaseUpdator getInstance() {
        return ourInstance;
    }

    private DBUpdator() {/*nothing*/ }

    public synchronized void updateDataBase(){
      try {
          FileOutputStream fout=new FileOutputStream(address);
          ObjectOutputStream outToFile=new ObjectOutputStream(fout);
          outToFile.writeObject(Server.profiles);
          outToFile.close();
          fout.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

}
