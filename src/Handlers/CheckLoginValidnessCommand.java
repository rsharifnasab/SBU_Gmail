package Handlers;
import BasicClasses.*;

public final class CheckLoginValidnessCommand implements Command {

    LoginInformation loginInformation;

    public CheckLoginValidnessCommand(LoginInformation loginInformation) {
        this.loginInformation = loginInformation;
    }

    public LoginInformation getLoginInformation() {
        return loginInformation;
    }

    public void setLoginInformation(LoginInformation loginInformation) {
        this.loginInformation = loginInformation;
    }

}
