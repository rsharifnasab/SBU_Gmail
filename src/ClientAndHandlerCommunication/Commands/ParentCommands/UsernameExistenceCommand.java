package ClientAndHandlerCommunication.Commands.ParentCommands;

import ClientAndHandlerCommunication.Commands.Command;

public final class UsernameExistenceCommand implements Command {

    String username;

    public UsernameExistenceCommand(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
