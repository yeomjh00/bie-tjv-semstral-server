package social_network.web.exception_handler.exception;

public class InvalidAccessException extends IllegalArgumentException {
    public InvalidAccessException(String errorMessage) {
        super(errorMessage);
    }
}
