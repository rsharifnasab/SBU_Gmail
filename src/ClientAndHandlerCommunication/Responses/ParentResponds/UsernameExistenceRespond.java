package ClientAndHandlerCommunication.Responses.ParentResponds;

import ClientAndHandlerCommunication.Responses.Response;

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
