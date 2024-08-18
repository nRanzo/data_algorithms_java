package trees;

public class EmptyTreeException extends Exception {

    // Default constructor
    public EmptyTreeException() {
        super();
    }
    
    // Constructor that accepts a message
    public EmptyTreeException(String message) {
        super(message);
    }
    
    // Constructor that accepts a message and a cause
    public EmptyTreeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Constructor that accepts a cause
    public EmptyTreeException(Throwable cause) {
        super(cause);
    }
}
