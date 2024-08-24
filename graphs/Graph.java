/**
 * A generic interface for a graph data structure. The graph is defined by a set of vertices
 * and edges that connect pairs of vertices.
 *
 * @param <V> the type of vertices in the graph
 * @param <E> the type of edges in the graph
 */
public interface Graph<V, E> {

    /**
     * Returns the number of vertices in the graph.
     *
     * @return the number of vertices
     */
    int numVertices();

    /**
     * Returns an iterable collection of all the vertices in the graph.
     *
     * @return an iterable collection of vertices
     */
    Iterable<V> vertices();

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
     */
    E getEdge(V u, V v);

    /**
     * Returns an array containing the two end vertices of edge {@code e}.
     * If the graph is directed, the first vertex in the array is the origin
     * of the edge {@code e} and the second is its destination.
     *
     * @param e the edge whose end vertices are to be returned
     * @return an array of two vertices that are the endpoints of the edge {@code e}
     */
    V[] endVertices(E e);

    /**
     * Returns the vertex that is opposite vertex {@code v} on edge {@code e}.
     * Throws an error if {@code e} is not incident to {@code v}.
     *
     * @param v the vertex for which the opposite is to be found
     * @param e the edge connecting {@code v} to its opposite
     * @return the vertex that is opposite {@code v} on edge {@code e}
     * @throws IllegalArgumentException if {@code e} is not incident to {@code v}
     */
    V opposite(V v, E e);

    /**
     * Returns the number of outgoing edges from vertex {@code v}.
     *
     * @param v the vertex whose outgoing edges are to be counted
     * @return the number of outgoing edges from {@code v}
     */
    int outDegree(V v);

    /**
     * Returns the number of incoming edges to vertex {@code v}.
     * In an undirected graph, this method returns the same value as {@code outDegree(v)}.
     *
     * @param v the vertex whose incoming edges are to be counted
     * @return the number of incoming edges to {@code v}
     */
    int inDegree(V v);

    /**
     * Returns an iterable collection of all outgoing edges from vertex {@code v}.
     *
     * @param v the vertex whose outgoing edges are to be returned
     * @return an iterable collection of outgoing edges from {@code v}
     */
    Iterable<E> outgoingEdges(V v);

    /**
     * Returns an iterable collection that allows traversal of all incoming edges to vertex {@code v}.
     * In an undirected graph, this method returns the same collection as {@code outgoingEdges(v)}.
     *
     * @param v the vertex whose incoming edges are to be returned
     * @return an iterable collection of incoming edges to {@code v}
     */
    Iterable<E> incomingEdges(V v);

    /**
     * Creates and returns a new vertex object that stores the element {@code x}.
     *
     * @param x the element to be stored in the new vertex
     * @return the newly created vertex
     */
    V insertVertex(V x);

    /**
     * Creates and returns a new edge object that stores the element {@code x} and represents an edge
     * from vertex {@code u} to vertex {@code v}. Throws an error if an edge from {@code u} to {@code v} already exists.
     *
     * @param u the origin vertex
     * @param v the destination vertex
     * @param x the element to be stored in the new edge
     * @return the newly created edge
     * @throws IllegalArgumentException if an edge from {@code u} to {@code v} already exists
     */
    E insertEdge(V u, V v, E x);

    /**
     * Removes vertex {@code v} from the graph, along with all edges incident to it.
     *
     * @param v the vertex to be removed
     * @throws IllegalArgumentException if the vertex {@code v} does not exist in the graph
     */
    void removeVertex(V v);

    /**
     * Removes edge {@code e} from the graph.
     *
     * @param e the edge to be removed
     * @throws IllegalArgumentException if the edge {@code e} does not exist in the graph
     */
    void removeEdge(E e);
}
