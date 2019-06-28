package Handlers;

public final class ChangeStateResponse implements Response {

    private boolean isSuccessful;

    public ChangeStateResponse( boolean isSuccessful ) {
        this.setSuccessful( isSuccessful );
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful( boolean successful ) {
        isSuccessful = successful;
    }

}
