package BasicClasses;

import java.io.Serializable;

public class Time implements Serializable {
    private int minutes;
    private int seconds;

    public Time(int timeInSeconds){
        this.seconds=timeInSeconds%60;
        this.minutes=timeInSeconds/60;

    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getTimeInseconds(){
        return this.seconds+(this.minutes*60);
    }


    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
