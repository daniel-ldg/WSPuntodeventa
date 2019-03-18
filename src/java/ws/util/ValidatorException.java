package ws.util;

public class ValidatorException extends Exception {

    private final int errorCode;

    public ValidatorException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
    
}
