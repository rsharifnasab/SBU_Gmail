package Enums;

public enum Color {
    BLACK,WHITE;

    @Override
    public String toString() {
        switch ( this ){
            case BLACK : return "black";
            case WHITE : return "white";
            default: throw new IllegalArgumentException();
        }
    }

}
