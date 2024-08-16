package priorityqueue;

public class EmptyPriorityQueueException extends RuntimeException {

    // Default constructor
    public EmptyPriorityQueueException() {
        super("Priority queue is empty.");
    }

    // Constructor that accepts a custom message
    public EmptyPriorityQueueException(String message) {
        super(message);
    }

    // Constructor that accepts a cause
    public EmptyPriorityQueueException(Throwable cause) {
        super(cause);
    }

    // Constructor that accepts both a custom message and a cause
    public EmptyPriorityQueueException(String message, Throwable cause) {
        super(message, cause);
    }
}
