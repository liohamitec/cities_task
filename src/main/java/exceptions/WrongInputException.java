package exceptions;

public class WrongInputException extends RuntimeException {
    private String msg;

    public WrongInputException(String msg) {
        this.msg = msg;
    }
    public String toString() {
        return "WrongInputException: " + msg;
    }
}
