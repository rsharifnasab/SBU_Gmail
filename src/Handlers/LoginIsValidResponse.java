package Handlers;

public final class LoginIsValidResponse implements Response {

    private boolean answer;

    public LoginIsValidResponse(boolean answer) {
        this.setAnswer( answer );
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

}
