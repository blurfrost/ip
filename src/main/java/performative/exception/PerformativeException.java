package performative.exception;

/**
 * Represents an exception specific to the Performative application.
 * Thrown when errors occur during task parsing or execution within the application.
 */
public class PerformativeException extends Exception {
    /**
     * Constructs a new PerformativeException with the specified detail message.
     *
     * @param message The detail message explaining the cause of the exception.
     */
    public PerformativeException(String message) {
        super(message);
    }
}
