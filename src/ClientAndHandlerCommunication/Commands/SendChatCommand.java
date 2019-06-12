package ClientAndHandlerCommunication.Commands;

import Enums.ChatChannelType;
import Game.Match;

public final class SendChatCommand implements Command {
    private String msg;
    private String sender;
    private Match match;
    private ChatChannelType chatChannelType;
    public SendChatCommand(String msg,Match match,ChatChannelType chatChannelType,String sender){

        this.sender=sender;
        this.msg=msg;
        this.match=match;
        this.chatChannelType=chatChannelType;

    }

    public Match getMatch() {
        return match;
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
