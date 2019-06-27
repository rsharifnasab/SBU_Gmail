package Handlers;
//TODO


public final class SendChatCommand implements Command {
    private String msg;
    private String sender;

    public SendChatCommand(String msg,String sender){

        this.sender=sender;
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getSender() {
        return sender;
    }
}
