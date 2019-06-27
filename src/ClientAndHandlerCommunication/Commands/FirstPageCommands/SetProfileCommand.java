package ClientAndHandlerCommunication.Commands.FirstPageCommands;

import ClientAndHandlerCommunication.Commands.Command;
import BasicClasses.*;
public final class SetProfileCommand implements Command {

    private Profile profile;

    public SetProfileCommand(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
