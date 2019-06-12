package BasicClasses;

import java.io.Serializable;

public class Timer implements Serializable {
   private long initTime=0;
 public Timer(){/* empty */ }

    public void start(){
        initTime=System.nanoTime();
    }

    public Time getTime(){
        long nowsTimeInMilis=System.nanoTime();
        int sec=(int) ((nowsTimeInMilis-this.initTime)/1e9);

        Time nowsTime=new Time(sec);
        return nowsTime;
    }

}
