package Handlers;

public class UsernameExistenceRespond implements Response {

    boolean answer;

    public UsernameExistenceRespond(boolean answer) {
        this.answer = answer;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

}
