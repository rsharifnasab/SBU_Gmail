package Handlers;

import Enums.ChatChannelType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public final class RecieveChatCommand implements Command {

    private ChatChannelType channelType;
    private String msg;
    private String sender;

    public RecieveChatCommand(String msg,String sender,ChatChannelType channelType){
        this.channelType=channelType;
        this.sender=sender;
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getSender() {
        return sender;
    }

    public ChatChannelType getChannelType() {
        return channelType;
    }
}
