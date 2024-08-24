### Introduction to Graphs

Graphs are among the most complex data structures in computer science. It is highly recommended that you study this topic after gaining a solid understanding of other data structures, particularly trees. Mastering trees will provide you with the necessary foundation to grasp the concepts and intricacies of graphs. Therefore, once you are confident in your knowledge of trees, you can return to this section on Graphs vs. Trees with a deeper comprehension of the material.

If you are already comfortable with tree implementation, that's great! However, before moving forward, I encourage you to at least review the trees folder to ensure you are fully prepared for the advanced concepts covered in graphs.

Graphs and trees are both nonlinear data structures that represent sets of nodes (or vertices) and connections (or edges) between them, but they differ significantly in terms of structure, properties, and usage.

### Definition and Structure

Trees:

    A tree is a special type of graph, specifically an acyclic connected graph.

    It consists of nodes connected by edges, with a designated "root" node.

    Every node (except the root) has exactly one parent.

    Trees contain no cycles: there is a single path between any two nodes.

    A tree with 𝑛 nodes has 𝑛−1 edges.

    Common examples include binary trees, binary search trees (BST), and AVL trees.

Graphs:

    A graph is a more general structure consisting of a set of nodes connected by edges, which can be directed or undirected.

    It has no inherent root or hierarchy.

    Graphs may contain cycles (closed paths where you can return to the starting point) or be acyclic.

    They can be connected (there is a path between every pair of nodes) or disconnected.

    Graphs can have weighted edges (weighted graphs) and can be directed (with directional edges).

    Common examples include transportation networks, social networks, and dependency schemes in projects.

    Graphs DO NOT implement Container, because its size it's not well defined (size of vertex? edges?)


### Key Properties and Main Differences

Connectivity:

    A tree is always connected; there is a unique path between every pair of nodes.

    A graph can be connected or disconnected; there may be multiple paths or no path between two nodes.

Cycles:

    A tree is cycle-free.
    A graph can contain cycles.

Edge Direction:

    Trees have an inherent directional flow, with implicit edges going from the root to children.

    Graphs can have directed (with orientation) or undirected edges.

Hierarchy:

    A tree is hierarchical, with a clear "parent-child" relationship.

    Graphs do not have an inherent hierarchy.

Number of Edges:

    In a tree, the number of edges is exactly 𝑛−1 for 𝑛 nodes.

    In graphs, the number of edges varies and can be much larger or smaller.

### Exception Handling

Exception Handling in Trees and Graphs

In both Trees and Graphs, it’s important to throw specific exceptions or define custom exceptions to handle errors effectively.

Trees:

    Use EmptyTreeException and InvalidPositionException.

Graphs:

    Use InvalidEdgeException and InvalidVertexException.

This approach ensures that exceptions are thrown immediately when an issue arises, making it easier to identify and debug errors during development. You can also throw IllegalArgumentException with a message if you prefer, but at least provide that exception with a message "edge not valid" or "vertex not valid".

### Uses and Applications

Trees:

    Used when data has a hierarchical structure: file systems, family trees, decision trees.

    Search trees (like BST, AVL) are used for efficient search, insertion, and deletion operations.

Graphs:

    Used to represent complex networks without rigid hierarchies: communication networks, social networks, road maps.

    Graph algorithms (like Dijkstra, Bellman-Ford, BFS, DFS) are used to find shortest paths, maximum flows, and to explore networks.

### Conclusion

While trees are highly specialized and optimized structures for representing hierarchical data and supporting fast search operations, graphs provide a more general and flexible representation of any network of interconnected nodes, allowing for cycles, weights, and directional edges. This versatility makes graphs essential for modeling and solving complex problems in computer science where relationships between data are not strictly hierarchical.

Quite often the more flexible solution you want, the harder you must study. Advancing to more powerful tools, like graphs, demands a solid foundation and commitment to learning!

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

### EXTRA: Spanning Tree vs. Spanning Subgraph

Both spanning trees and spanning subgraphs are concepts derived from graph theory, but they have distinct definitions and properties.

Spanning Subgraph
    
    Definition: A spanning subgraph of a graph 𝐺=(𝑉,𝐸) is a subgraph that includes all the vertices 𝑉 of the original graph 𝐺. However, it may contain a subset of the edges 𝐸 from 𝐺.

    Properties:
        It includes every vertex from the original graph.
        
        It can have fewer edges than the original graph but never more.
    
        A spanning subgraph may still contain cycles, multiple connected components, and different degrees of connectivity.

    Use Case: Spanning subgraphs are useful in scenarios where you want to preserve all the vertices of a graph but simplify the graph by removing some edges for easier analysis or different applications.

Spanning Tree

    Definition: A spanning tree of a graph 𝐺=(𝑉,𝐸) is a type of spanning subgraph that is a tree. It includes all the vertices of 𝐺 but is also:

        Connected: There is a path between every pair of vertices.
        
        Acyclic: It contains no cycles.
        
        Minimal: A spanning tree has exactly 𝑉−1 edges, where 𝑉 is the number of vertices in the graph.

    Properties:

        It is a connected, acyclic subgraph.
        
        It is a minimal connected subgraph that spans all the vertices.
        
        Every graph can have multiple spanning trees.
        
        Removing any edge from a spanning tree will disconnect it, while adding any edge will create a cycle.

    Use Case: Spanning trees are crucial in network design, where you want to ensure all points (vertices) are connected with the minimum number of connections (edges). Examples include constructing efficient communication networks, routing algorithms, and minimizing wiring or connection costs.

Key Differences:
    
    Edge Count:

        Spanning Subgraph: Can have any number of edges between 𝑉−1 (minimum for connected graphs) and ∣𝐸∣ (the total number of edges in the original graph).

        Spanning Tree: Has exactly 𝑉−1 edges.

    Cycles:

        Spanning Subgraph: May contain cycles.
        
        Spanning Tree: Cannot contain cycles.
    
    Connectivity:

        Spanning Subgraph: May or may not be connected.

        Spanning Tree: Must be connected.

In summary, while both concepts involve subgraphs that include all the vertices of the original graph, a spanning tree is a more specific type of spanning subgraph with strict properties of connectivity and acyclicity, making it a vital tool in various applications that require efficient and minimal connectivity.