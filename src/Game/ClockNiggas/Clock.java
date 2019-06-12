package Game.ClockNiggas;

import BasicClasses.Time;
import BasicClasses.Timer;

import java.io.Serializable;

public class Clock implements Serializable {

    private Time roundTime;
    private Timer timer;

    public Clock(Time roundTime){
        this.roundTime=roundTime;
        timer=new Timer();
    }

    public void start(){
        this.timer.start();
    }

    public Time getTime(){
        int roundTimeInSec=(roundTime.getTimeInseconds());
        int remainingTime= roundTimeInSec-timer.getTime().getTimeInseconds();
        Time time=new Time(remainingTime);
        return time;
    }

    @Override
    public String toString() {
        return this.roundTime.getMinutes()+"m "+this.roundTime.getSeconds()+"s ";
    }
}
