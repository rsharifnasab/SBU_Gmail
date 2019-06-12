package NetworkStuff.ServerSide.SemiDataBase;

import NetworkStuff.ServerSide.Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class DataBaseUpdator {
    private static DataBaseUpdator ourInstance = new DataBaseUpdator();
    private static final String address="src/NetworkShit/ServerSide/SemiDataBase/Data";
    public static DataBaseUpdator getInstance() {
        return ourInstance;
    }

    private DataBaseUpdator() {
    }

        public  synchronized void  updateDataBase(){
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
