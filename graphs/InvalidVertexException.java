/**
 * Exception thrown when an invalid vertex is encountered in a graph operation.
 * This can occur when a vertex does not exist in the graph or when an operation
 * is attempted on a vertex that is not part of the graph.
 */
public class InvalidVertexException extends RuntimeException {

    /**
     * Constructs a new InvalidVertexException with {@code null} as its detail message.
     */
    public InvalidVertexException() {
        super();
    }

    /**
     * Constructs a new InvalidVertexException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidVertexException(String message) {
        super(message);
    }
}
