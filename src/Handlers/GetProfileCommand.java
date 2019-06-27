package Handlers;

import BasicClasses.*;

public final class GetProfileCommand implements Command {

    String userName;

    public GetProfileCommand(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
