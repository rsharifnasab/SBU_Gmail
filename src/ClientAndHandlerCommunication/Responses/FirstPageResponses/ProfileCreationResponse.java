
package ClientAndHandlerCommunication.Responses.FirstPageResponses;

import ClientAndHandlerCommunication.Responses.Response;

public final class ProfileCreationResponse implements Response {

    private String message;
    private boolean wasSuccessful;

    public ProfileCreationResponse(boolean wasSuccessful, String message) {
        this.setWasSuccessful( wasSuccessful );
        this.setMessage( message );
    }

    public boolean isWasSuccessful() {
        return wasSuccessful;
    }

    public void setWasSuccessful(boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
