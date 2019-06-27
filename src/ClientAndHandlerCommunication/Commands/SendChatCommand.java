package ClientAndHandlerCommunication.Commands;

import Enums.ChatChannelType;

public final class SendChatCommand implements Command {
    private String msg;
    private String sender;
    private ChatChannelType chatChannelType;
    public SendChatCommand(String msg,ChatChannelType chatChannelType,String sender){

        this.sender=sender;
        this.msg=msg;
        this.chatChannelType=chatChannelType;

    }

    public ChatChannelType getChatChannelType() {
        return chatChannelType;
    }

    public String getMsg() {
        return msg;
    }

    public String getSender() {
        return sender;
    }
}
