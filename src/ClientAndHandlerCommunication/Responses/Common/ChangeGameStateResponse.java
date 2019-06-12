package ClientAndHandlerCommunication.Responses.Common;

import ClientAndHandlerCommunication.Responses.Response;

public final class ChangeGameStateResponse implements Response {

    private boolean isSuccessful;

    public ChangeGameStateResponse( boolean isSuccessful ) {
        this.setSuccessful( isSuccessful );
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful( boolean successful ) {
        isSuccessful = successful;
    }

}
