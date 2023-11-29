package domain.server.exceptions;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        // Constructor vacío
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    // Constructor con un mensaje y una causa
    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
