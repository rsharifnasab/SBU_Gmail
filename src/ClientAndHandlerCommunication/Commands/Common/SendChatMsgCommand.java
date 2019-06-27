package ClientAndHandlerCommunication.Commands.Common;

import ClientAndHandlerCommunication.Commands.Command;
import BasicClasses.*;
import java.util.List;

public final class SendChatMsgCommand implements Command {
    private String massage;
    private List<Profile> listeners;


    SendChatMsgCommand(String massage,List listeners){
        this.massage=massage;
        this.listeners=listeners;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public List<Profile> getListeners() {
        return listeners;
    }

    public void setListeners(List<Profile> listeners) {
        this.listeners = listeners;
    }
}
