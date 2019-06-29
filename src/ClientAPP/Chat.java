import Connection.Connection;

import java.util.Scanner;

public class Chat {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String username = in.nextLine();
        Connection user = new Connection(username);
        user.initializeServices();

        String message = "";
        while (!message.equals("disconnect") && in.hasNextLine()) {
            message = in.nextLine();
            if (!message.equals("disconnect")) {
                String[] split = message.split(":");
                String receiver = split[0];
                String messageText = split[1];
                user.sendText(messageText, receiver);
            } else {
                user.disconnect();
            }
        }
    }
}