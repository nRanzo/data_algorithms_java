public class InvalidPositionException extends Exception {
    
    // Default constructor
    public InvalidPositionException() {
        super();
    }
    
    // Constructor that accepts a message
    public InvalidPositionException(String message) {
        super(message);
    }
    
    // Constructor that accepts a message and a cause
    public InvalidPositionException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Constructor that accepts a cause
    public InvalidPositionException(Throwable cause) {
        super(cause);
    }
}
