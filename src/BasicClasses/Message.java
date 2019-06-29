package BasicClasses;

import java.io.Serializable;

public class Message implements Serializable
{
    public static final long serialVersionUID = 14L;
    private String sender;
    private String receiver;
    private String messageText;

    private final MessageType messageType;

    public Message(MessageType messageType, String sender, String receiver, String messageText) {
        this.messageType = messageType;
        this.receiver = receiver;
        this.messageText = messageText;
        this.sender = sender;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", messageText='" + messageText + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}
