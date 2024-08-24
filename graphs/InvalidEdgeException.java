/**
 * Exception thrown when an invalid edge is encountered in a graph operation.
 * This can occur when an edge does not exist in the graph or when an operation
 * is attempted on an edge that is not part of the graph.
 */
public class InvalidEdgeException extends RuntimeException {

    /**
     * Constructs a new InvalidEdgeException with {@code null} as its detail message.
     */
    public InvalidEdgeException() {
        super();
    }

    /**
     * Constructs a new InvalidEdgeException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidEdgeException(String message) {
        super(message);
    }
}
