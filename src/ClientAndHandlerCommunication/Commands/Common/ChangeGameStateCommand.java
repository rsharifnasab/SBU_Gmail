
package ClientAndHandlerCommunication.Commands.Common;

import ClientAndHandlerCommunication.Commands.Command;
import Enums.GameState;

public final class ChangeGameStateCommand implements Command {

    private GameState gameState;

    public ChangeGameStateCommand(GameState gameState ) {
        this.setGameState( gameState );
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}
