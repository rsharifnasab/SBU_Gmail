
package Handlers;

import Enums.State;

public final class ChangeStateCommand implements Command {

    private State state;

    public ChangeStateCommand(State state ) {
        this.setState( state );
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
