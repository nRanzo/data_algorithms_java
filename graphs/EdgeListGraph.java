import java.util.ArrayList;
import java.util.List;

/**
 * Edge list implementation of a graph data structure. The graph is defined by a set of vertex
 * and edges that connect pairs of vertex.
 *
 * @param <V> the type of vertex in the graph
 * @param <E> the type of edges in the graph
 */
public class EdgeListGraph<V, E> implements Graph<V, E> {

    /**
     * Inner class representing a Vertex in the graph.
     */
    private class Vertex {
        V element;

        Vertex(V element) {
            this.element = element;
        }
    }

    /**
     * Inner class representing an Edge in the graph.
     */
    private class Edge {
        Vertex u;
        Vertex v;
        E element;

        Edge(Vertex u, Vertex v, E element) {
            this.u = u;
            this.v = v;
            this.element = element;
        }
    }

    private List<Vertex> vertexList; // List of all vertices
    private List<Edge> edgeList;     // List of all edges

    /**
     * Constructor for EdgeListGraph. Initializes empty vertex and edge lists.
     */
    public EdgeListGraph() {
        vertexList = new ArrayList<>();
        edgeList = new ArrayList<>();
    }

    /**
     * Returns the number of vertices in the graph. Time complexity O(1).
     *
     * @return the number of vertices
     */
    @Override
    public int numVertex() {
        return vertexList.size();
    }

    /**
     * Returns an iterable collection of all the vertices in the graph. Time complexity O(n).
     *
     * @return an iterable collection of vertices
     */
    @Override
    public Iterable<V> vertex() {
        List<V> elements = new ArrayList<>();
        for (Vertex v : vertexList) {
            elements.add(v.element);
        }
        return elements;
    }

    /**
     * Returns the number of edges in the graph. Time complexity O(1).
     *
     * @return the number of edges
     */
    @Override
    public int numEdges() {
        return edgeList.size();
    }

    /**
     * Returns an iterable collection of all the edges in the graph. Time complexity O(m).
     *
     * @return an iterable collection of edges
     */
    @Override
    public Iterable<E> edges() {
        List<E> elements = new ArrayList<>();
        for (Edge e : edgeList) {
            elements.add(e.element);
        }
        return elements;
    }

    /**
     * Returns the edge from vertex {@code u} to vertex {@code v}, or {@code null} if no such edge exists.
     * Time complexity O(m).
     *
     * @param u the first vertex
     * @param v the second vertex
     * @return the edge from {@code u} to {@code v}, or {@code null} if no such edge exists
     * @throws InvalidVertexException if {@code u} or {@code v} are invalid vertices
     */
    @Override
    public E getEdge(V u, V v) throws InvalidVertexException {
        Vertex vertexU = validateVertex(u);
        Vertex vertexV = validateVertex(v);
        for (Edge e : edgeList) {
            if (e.u == vertexU && e.v == vertexV) {
                return e.element;
            }
        }
        return null; // Edge does not exist
    }

    /**
     * Returns an array containing the two end vertices of edge {@code e}.
     * If the graph is directed, the first vertex in the array is the origin
     * of the edge {@code e} and the second is its destination.
     *
     * @param e the edge whose end vertices are to be returned
     * @return an array of two vertices that are the endpoints of the edge {@code e}
     * @throws InvalidEdgeException if {@code e} is an invalid edge
     */
    @SuppressWarnings("unchecked")
    @Override
    public V[] endVertex(E e) throws InvalidEdgeException {
        Edge edge = validateEdge(e);
        return (V[]) new Object[]{edge.u.element, edge.v.element};  // should be safe
    }

    /**
     * Returns the vertex that is opposite vertex {@code v} on edge {@code e}.
     *
     * @param v the vertex for which the opposite is to be found
     * @param e the edge connecting {@code v} to its opposite
     * @return the vertex that is opposite {@code v} on edge {@code e}
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     * @throws InvalidEdgeException if {@code e} is an invalid edge or not incident to {@code v}
     */
    @Override
    public V opposite(V v, E e) throws InvalidVertexException, InvalidEdgeException {
        Vertex vertex = validateVertex(v);
        Edge edge = validateEdge(e);
        if (edge.u == vertex) return edge.v.element;
        if (edge.v == vertex) return edge.u.element;
        throw new InvalidEdgeException("The edge is not incident to the vertex.");
    }

    /**
     * Returns the number of outgoing edges from vertex {@code v}. Time complexity O(m).
     *
     * @param v the vertex whose outgoing edges are to be counted
     * @return the number of outgoing edges from {@code v}
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     */
    @Override
    public int outDegree(V v) throws InvalidVertexException {
        Vertex vertex = validateVertex(v);
        int count = 0;
        for (Edge e : edgeList) {
            if (e.u == vertex) count++;
        }
        return count;
    }

    /**
     * Returns the number of incoming edges to vertex {@code v}. Time complexity O(m).
     * In an undirected graph, this method returns the same value as {@code outDegree(v)}.
     *
     * @param v the vertex whose incoming edges are to be counted
     * @return the number of incoming edges to {@code v}
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     */
    @Override
    public int inDegree(V v) throws InvalidVertexException {
        Vertex vertex = validateVertex(v);
        int count = 0;
        for (Edge e : edgeList) {
            if (e.v == vertex) count++;
        }
        return count;
    }

    /**
     * Returns an iterable collection of all outgoing edges from vertex {@code v}. Time complexity O(m).
     *
     * @param v the vertex whose outgoing edges are to be returned
     * @return an iterable collection of outgoing edges from {@code v}
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     */
    @Override
    public Iterable<E> outgoingEdges(V v) throws InvalidVertexException {
        Vertex vertex = validateVertex(v);
        List<E> edges = new ArrayList<>();
        for (Edge e : edgeList) {
            if (e.u == vertex) edges.add(e.element);
        }
        return edges;
    }

    /**
     * Returns an iterable collection that allows traversal of all incoming edges to vertex {@code v}.
     * In an undirected graph, this method returns the same collection as {@code outgoingEdges(v)}.
     * Time complexity O(m).
     *
     * @param v the vertex whose incoming edges are to be returned
     * @return an iterable collection of incoming edges to {@code v}
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     */
    @Override
    public Iterable<E> incomingEdges(V v) throws InvalidVertexException {
        Vertex vertex = validateVertex(v);
        List<E> edges = new ArrayList<>();
        for (Edge e : edgeList) {
            if (e.v == vertex) edges.add(e.element);
        }
        return edges;
    }

    /**
     * Creates and returns a new vertex object that stores the element {@code x}. Time complexity O(1).
     *
     * @param x the element to be stored in the new vertex
     * @return the newly created vertex
     */
    @Override
    public V insertVertex(V x) {
        Vertex vertex = new Vertex(x);
        vertexList.add(vertex);
        return x;
    }

    /**
     * Creates and returns a new edge object that stores the element {@code x} and represents an edge
     * from vertex {@code u} to vertex {@code v}. Time complexity O(1).
     *
     * @param u the origin vertex
     * @param v the destination vertex
     * @param x the element to be stored in the new edge
     * @return the newly created edge
     * @throws InvalidVertexException if {@code u} or {@code v} are invalid vertices
     * @throws IllegalArgumentException if an edge from {@code u} to {@code v} already exists
     */
    @Override
    public E insertEdge(V u, V v, E x) throws InvalidVertexException, IllegalArgumentException {
        Vertex vertexU = validateVertex(u);
        Vertex vertexV = validateVertex(v);

        if (getEdge(u, v) != null) {
            throw new IllegalArgumentException("Edge already exists between u and v.");
        }

        Edge edge = new Edge(vertexU, vertexV, x);
        edgeList.add(edge);
        return x;
    }

    /**
     * Removes vertex {@code v} from the graph, along with all edges incident to it. Time complexity O(m).
     *
     * @param v the vertex to be removed
     * @throws InvalidVertexException if {@code v} is an invalid vertex
     */
    @Override
    public void removeVertex(V v) throws InvalidVertexException {
        Vertex vertex = validateVertex(v);

        // Remove all edges incident to this vertex
        edgeList.removeIf(e -> e.u == vertex || e.v == vertex);

        // Remove the vertex itself
        vertexList.remove(vertex);
    }

    /**
     * Removes edge {@code e} from the graph. Time complexity O(1).
     *
     * @param e the edge to be removed
     * @throws InvalidEdgeException if {@code e} is an invalid edge
     */
    @Override
    public void removeEdge(E e) throws InvalidEdgeException {
        Edge edge = validateEdge(e);
        edgeList.remove(edge);
    }

    /**
     * Validates if the given vertex is valid in the graph. Time complexity O(V), where V is the number of vertices.
     * In the worst case, it may need to check every vertex in the list.
     * 
     * @param v the vertex to be validated
     * @return the Vertex object corresponding to the given vertex element
     * @throws InvalidVertexException if the vertex is not found
     */
    private Vertex validateVertex(V v) throws InvalidVertexException {
        for (Vertex vertex : vertexList) {
            if (vertex.element.equals(v)) {
                return vertex;
            }
        }
        throw new InvalidVertexException("Vertex not found.");
    }

    /**
     * Validates if the given edge is valid in the graph. Time complexity O(E), where E is the number of edges.
     * In the worst case, it may need to check every edge in the list.
     *
     * @param e the edge to be validated
     * @return the Edge object corresponding to the given edge element
     * @throws InvalidEdgeException if the edge is not found
     */
    private Edge validateEdge(E e) throws InvalidEdgeException {
        for (Edge edge : edgeList) {
            if (edge.element.equals(e)) {
                return edge;
            }
        }
        throw new InvalidEdgeException("Edge not found.");
    }
}
