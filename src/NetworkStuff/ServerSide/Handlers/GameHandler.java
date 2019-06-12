package NetworkStuff.ServerSide.Handlers;

import ClientAndHandlerCommunication.Commands.Command;
import ClientAndHandlerCommunication.Commands.MadeAMoveCommand;
import Game.Match;
import Game.Profile;
import NetworkStuff.ServerSide.Log.ServerLogWriter;
import NetworkStuff.ServerSide.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameHandler implements Runnable{

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private Socket gameSocket;

    public GameHandler(Socket gameSocket){
        this.gameSocket =gameSocket;
        try {

            oos=new ObjectOutputStream(gameSocket.getOutputStream());
            ois=new ObjectInputStream(gameSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while ( true ) {
            Command command = null;
            try {
                command = (Command) this.ois.readObject();
            } catch (IOException|ClassNotFoundException e) {
                //e.printStackTrace();
            }
            if ( command instanceof MadeAMoveCommand ){
                Match match = ((MadeAMoveCommand) command).getMatch();
                ServerLogWriter.getInstance().writeLog("User: "+((MadeAMoveCommand) command).getMatch().getCurrentPlayer().getUserName()+" made a move! ");
                try {
                    System.out.println( match.getHostProfile() );
                    System.out.println( match.getGuestProfile() );
                    Server.gameHandlers.get( Server.userHandlers.get( match.getHostProfile() ) ).oos.writeObject( command );
                    Server.gameHandlers.get( Server.userHandlers.get( match.getGuestProfile() ) ).oos.writeObject( command );
                    for ( Profile audience : match.getAudience() )
                        Server.gameHandlers.get( Server.userHandlers.get( audience ) ).oos.writeObject( command );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
