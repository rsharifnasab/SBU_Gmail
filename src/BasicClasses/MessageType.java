package BasicClasses;

public enum MessageType
{
    /**
     * connect to a user
     */
    Connect,
    /**
     * disconnect user from server
     */
    Disconnect,
    /**
     * send a text message
     */
    Text,
    /**
     * send a message to indicate error
     */
    Error,
}
