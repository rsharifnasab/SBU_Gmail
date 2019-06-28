package Network.ServerSide.Handlers;

import Handlers.*;
import BasicClasses.*;
import Network.ServerSide.*;
import java.io.*;
import java.net.*;

public class ChatHandler implements Runnable {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private Socket chatSocket;

    public ChatHandler(Socket chatSocket) {
        this.chatSocket = chatSocket;
        try {
            oos = new ObjectOutputStream(chatSocket.getOutputStream());
            ois = new ObjectInputStream(chatSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        try {

            while (true) {
                SendChatCommand chatCommand = this.getChat();
                sendChat(chatCommand);
            }
        } catch (IOException e) {
            if (e instanceof EOFException) {
                System.out.println("join socket disconnected.");
            }

        }
    }

    private SendChatCommand getChat()throws IOException{
    return null;
    //TODO
    }

    private void sendChat(SendChatCommand chatCommand)throws IOException{
      //TODO
    }
}
