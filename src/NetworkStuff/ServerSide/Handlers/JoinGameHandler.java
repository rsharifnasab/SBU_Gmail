package NetworkStuff.ServerSide.Handlers;

import ClientAndHandlerCommunication.Commands.Command;
import ClientAndHandlerCommunication.Commands.JoinGameCommand;
import ClientAndHandlerCommunication.Responses.JoinedGameResponse;
import ClientAndHandlerCommunication.Responses.Response;
import Enums.JoinerType;
import Game.Match;
import Game.Profile;
import NetworkStuff.ServerSide.Log.ServerLogWriter;
import NetworkStuff.ServerSide.Server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class JoinGameHandler implements Runnable {



    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private Socket joinGameSocket;

    public JoinGameHandler(Socket joinGameSocket){
        this.joinGameSocket=joinGameSocket;
        try {
            oos=new ObjectOutputStream(joinGameSocket.getOutputStream());
            ois=new ObjectInputStream(joinGameSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @Override
    public void run() {
        try {

            while (true) {

                Command clientCommand = this.getCommandFromClient();
                Response response = this.produceResponse(clientCommand);
                this.sendResponseToClient(response);

            }
        }
        catch (IOException e){
            if (e instanceof EOFException){
                System.out.println("join socket disconnected.");
            }
        }

    }


    private Command getCommandFromClient() throws IOException {
        Command returnValue = null;
        try {
            returnValue = (Command) this.ois.readObject();

        } catch (ClassNotFoundException e) {
                e.printStackTrace();
        }
        return returnValue;
    }

    private void sendResponseToClient( Response response)throws IOException {

            JoinedGameResponse joinedGameResponse=(JoinedGameResponse) response;
            if (joinedGameResponse.getJoinerType()== JoinerType.GUEST){
                for (Match match:Server.challenges.keySet()) {
                    if (joinedGameResponse.getMatch().equals(match)) {
                        match.setGuestProfile(joinedGameResponse.getProfile());
                        joinedGameResponse.getMatch().setGuestProfile(joinedGameResponse.getProfile());
                        joinedGameResponse.getMatch().getAudience().clear();
                        joinedGameResponse.getMatch().getAudience().addAll(match.getAudience());

                        for (Profile audience : match.getAudience()){
                            JoinGameHandler targetHandler=Server.joinGameHandlers.get(Server.userHandlers.get(audience));
                            targetHandler.oos.writeObject(joinedGameResponse);
                        }
                        JoinGameHandler targetHandler=Server.joinGameHandlers.get(Server.userHandlers.get(joinedGameResponse.getMatch().getHostProfile()));
                        targetHandler.oos.writeObject(joinedGameResponse);
                        ServerLogWriter.getInstance().writeLog("User: "+joinedGameResponse.getProfile().getUserName()+" joined "+match.getHostProfile().getUserName()+"s game. ");
                        this.oos.writeObject(joinedGameResponse);
                        break;
                    }

                }

            }

            else {
                for (Match match:Server.challenges.keySet()){
                    if (joinedGameResponse.getMatch().equals(match)) {
                        match.getAudience().add(joinedGameResponse.getProfile());
                        joinedGameResponse.getMatch().getAudience().clear();
                        joinedGameResponse.getMatch().getAudience().addAll(match.getAudience());
                        for (Profile audience : match.getAudience()){
                            JoinGameHandler targetHandler=Server.joinGameHandlers.get(Server.userHandlers.get(audience));
                            targetHandler.oos.writeObject(joinedGameResponse);
                        }
                        JoinGameHandler targetHandler=Server.joinGameHandlers.get(Server.userHandlers.get(joinedGameResponse.getMatch().getHostProfile()));
                        targetHandler.oos.writeObject(joinedGameResponse);
                        this.oos.writeObject(joinedGameResponse);

                        if (match.getGuestProfile()!=null){
                            joinedGameResponse.getMatch().setGuestProfile(match.getGuestProfile());
                             targetHandler=Server.joinGameHandlers.get(Server.userHandlers.get(joinedGameResponse.getMatch().getGuestProfile()));
                            targetHandler.oos.writeObject(joinedGameResponse);
                        }
                        ServerLogWriter.getInstance().writeLog("User: "+joinedGameResponse.getProfile().getUserName()+" joined "+match.getHostProfile().getUserName()+"s game. ");

                        break;
                    }
                }
            }


    }

    private Response produceResponse( Command command )throws IOException {
        Response returnValue = null;
        if (command instanceof JoinGameCommand){
            JoinGameCommand thiscommand=(JoinGameCommand) command;
            returnValue=new JoinedGameResponse(thiscommand.getMatch(),thiscommand.getProfile(),thiscommand.getJoinerType());
        }
        return returnValue;
    }

}
