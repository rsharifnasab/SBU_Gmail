package NetworkStuff.ServerSide.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ServerLogWriter {
    private static ServerLogWriter logWriterInstance=null;

    private static final String address="src/NetworkShit/ServerSide/Log/ServerLog";

    private ServerLogWriter(){

    }

    public static ServerLogWriter getInstance() {
        if (logWriterInstance==null)
            logWriterInstance=new ServerLogWriter();
        return logWriterInstance;
    }

    public  void writeLog(String massage){
        try {
            File file=new File(address);

            FileWriter fileWriter=new FileWriter(file,true);
            fileWriter.write(massage+"\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

