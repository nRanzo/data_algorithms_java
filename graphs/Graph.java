/**
 * A generic interface for a graph data structure. The graph is defined by a set of vertex
 * and edges that connect pairs of vertex.
 *
 * @param <V> the type of vertex in the graph
 * @param <E> the type of edges in the graph
 */
public interface Graph<V, E> {

    /**
     * Returns the number of vertex in the graph.
     *
     * @return the number of vertex
     */
    int numVertex();

    /**
     * Returns an iterable collection of all the vertex in the graph.
     *
     * @return an iterable collection of vertex
     */
    Iterable<V> vertex();

    /**
     * Returns the number of edges in the graph.
     *
     * @return the number of edges
     */
    int numEdges();

    /**
     * Returns an iterable collection of all the edges in the graph.
     *
     * @return an iterable collection of edges
     */
    Iterable<E> edges();

    /**
     * Returns the edge from vertex {@code u} to vertex {@code v}, or {@code null} if no such edge exists.
     *
     * @param u the first vertex
     * @param v the second vertex
     * @return the edge from {@code u} to {@code v}, or {@code null} if no such edge exists
     * @throws InvalidVertexException if {@code u} or {@code v} are invalid vertex
     */
    E getEdge(V u, V v) throws InvalidVertexException;

    /**
     * Returns an array containing the two end vertex of edge {@code e}.
     * If the graph is directed, the first vertex in the array is the origin
     * of the edge {@code e} and the second is its destination.
     *
     * @param e the edge whose end vertex are to be returned
     * @return an array of two vertex that are the endpoints of the edge {@code e}
     * @throws InvalidEdgeException if {@code e} is an invalid edge
     */
    V[] endVertex(E e) throws InvalidEdgeException;

    /**
     * Returns the vertex that is opposite vertex {@code v} on edge {@code e}.
     *
     * @param v the vertex for which the opposite is to be found
     * @param e the edge connecting {@code v} to its opposite
     * @return the vertex that is opposite {@code v} on edge {@code e}
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     * @throws InvalidEdgeException if {@code e} is an invalid edge or not incident to {@code v}
     */
    V opposite(V v, E e) throws InvalidVertexException, InvalidEdgeException;

    /**
     * Returns the number of outgoing edges from vertex {@code v}.
     *
     * @param v the vertex whose outgoing edges are to be counted
     * @return the number of outgoing edges from {@code v}
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     */
    int outDegree(V v) throws InvalidVertexException;

    /**
     * Returns the number of incoming edges to vertex {@code v}.
     * In an undirected graph, this method returns the same value as {@code outDegree(v)}.
     *
     * @param v the vertex whose incoming edges are to be counted
     * @return the number of incoming edges to {@code v}
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     */
    int inDegree(V v) throws InvalidVertexException;

    /**
     * Returns an iterable collection of all outgoing edges from vertex {@code v}.
     *
     * @param v the vertex whose outgoing edges are to be returned
     * @return an iterable collection of outgoing edges from {@code v}
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     */
    Iterable<E> outgoingEdges(V v) throws InvalidVertexException;

    /**
     * Returns an iterable collection that allows traversal of all incoming edges to vertex {@code v}.
     * In an undirected graph, this method returns the same collection as {@code outgoingEdges(v)}.
     *
     * @param v the vertex whose incoming edges are to be returned
     * @return an iterable collection of incoming edges to {@code v}
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     */
    Iterable<E> incomingEdges(V v) throws InvalidVertexException;

    /**
     * Creates and returns a new vertex object that stores the element {@code x}.
     *
     * @param x the element to be stored in the new vertex
     * @return the newly created vertex
     */
    V insertVertex(V x);

    /**
     * Creates and returns a new edge object that stores the element {@code x} and represents an edge
     * from vertex {@code u} to vertex {@code v}.
     *
     * @param u the origin vertex
     * @param v the destination vertex
     * @param x the element to be stored in the new edge
     * @return the newly created edge
     * @throws InvalidVertexException if {@code u} or {@code v} are invalid vertex
     * @throws IllegalArgumentException if an edge from {@code u} to {@code v} already exists
     */
    E insertEdge(V u, V v, E x) throws InvalidVertexException, IllegalArgumentException;

    /**
     * Removes vertex {@code v} from the graph, along with all edges incident to it.
     *
     * @param v the vertex to be removed
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     */
    void removeVertex(V v) throws InvalidVertexException;

    /**
     * Removes edge {@code e} from the graph.
     *
     * @param e the edge to be removed
     * @throws InvalidEdgeException if {@code e} is an invalid edge
     */
    void removeEdge(E e) throws InvalidEdgeException;
}
