package Exceptions;

import java.util.InputMismatchException;

public class IllegalTimeInput extends InputMismatchException {
    public IllegalTimeInput(){
        super();
    }

    @Override
    public String toString() {
        return "Dude... For the love of god...";
    }
}
