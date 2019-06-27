package NetworkStuff.ServerSide.SemiDataBase;

import NetworkStuff.ServerSide.Server;

import java.io.*;

public class DDUpdator {
    private static DBUpdator ourInstance = new DBUpdator();
    private static final String address="src/NetworkShit/ServerSide/SemiDataBase/Data";
    public static DataBaseUpdator getInstance() {
        return ourInstance;
    }

    private DataBaseUpdator() {/*nothing*/ }

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
