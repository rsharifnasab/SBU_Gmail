package ClientAndHandlerCommunication.Commands.FirstPageCommands;

import BasicClasses.LoginInformation;
import ClientAndHandlerCommunication.Commands.Command;

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
