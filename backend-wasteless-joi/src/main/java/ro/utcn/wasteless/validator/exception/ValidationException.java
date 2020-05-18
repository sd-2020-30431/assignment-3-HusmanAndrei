package ro.utcn.wasteless.validator.exception;

public class ValidationException extends RuntimeException {

    private String message;
    public ValidationException(String s) {
        super(s);
        message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
