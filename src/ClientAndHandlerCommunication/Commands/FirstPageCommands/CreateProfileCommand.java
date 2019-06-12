package ClientAndHandlerCommunication.Commands.FirstPageCommands;

import ClientAndHandlerCommunication.Commands.Command;
import Game.Profile;

public final class CreateProfileCommand implements Command {

    private Profile profile;

    public CreateProfileCommand(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
