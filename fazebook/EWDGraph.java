package fazebook;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

/*
Name: Cameron Vu (UID: 121227508)
Honor Pledge: I pledge on my honor that I have not given or received any 
              unauthorized assistance on this assignment.
*/


/**
 * The EWDGraph class is a representation of objects that depict directed
 * weighted graphs. The graphs contain data specified by the generic type, V. 
 * Through the body of this class, Collections, Comparators, HashMaps, and 
 * HashSets are used in order to construct vertices and edges, as well as to 
 * evaluate and retrieve data from the current EWDGraph object. Similarly, the 
 * EWDGraph class utilizes two fields -- a field adjacencyMap, which
 * represent the HashMap that stores the vertices and edges as keys and
 * values respectively, and the comparator field, which is instantiated 
 * in the EWDGraph constructor and stored for later use in the 
 * consolidateVertices() method. In all, the EWDGraph class contains methods
 * that allow modifications to the directed graph whilst also allowing 
 * for information pertaining to the current object, such as a Collections of
 * vertices, a Collection of neighbors, and an edge weight, just to name 
 * a few, to be found and returned to the user. 
 * 
 * @param <V>   A generic that specifies the object type that is specific to
 *              the current EWDGraph object, which must be consistent
 *              throughout operations on the current object.
 */
public class EWDGraph<V> {
    
    private HashMap<V, HashMap<V, Integer>> adjacencyMap;

    /**
     * Constructor that initializes the adjacencyMap field which is used to
     * represent the current graph. The method also initializes the comparator
     * field to the object that is used as the comparator argument. 
     * 
     * @param comparator    A Comparator object of type V that is stored in
     *                      the class field comparator for later use in the
     *                      consolidateVertices() method.
     */
    public EWDGraph(Comparator<V> comparator) {
        if (comparator == null)
            throw new IllegalArgumentException("Parameter is null");
        adjacencyMap = new HashMap<>();
    }
    
    /**
     * Adds the desired vertex to the current graph by adding the parameter 
     * to the adjacencyMap as the key. The vertex is not added if the value
     * already exists in the graph; thus, false is returned. Otherwise, the
     * object is added to the current data structure and true is returned.
     * 
     * @param vertexData    An object of type V that represents the desired 
     *                      added vertex.
     * @return  true if the vertex was successfully appended; false otherwise.
     */
    public boolean newEWDGraphVertex(V vertexData) {
        if (vertexData == null)
            throw new IllegalArgumentException();
        boolean added = true;
        // If the vertex is not already present, add the specified vertexData
        if (!adjacencyMap.containsKey(vertexData)) {
            adjacencyMap.put(vertexData, new HashMap<V, Integer>());
        } else {
            added = false;
        }
        return added;
    }

    /**
     * Checks if the provided vertex is present in the current EWDGraph 
     * object and returns true or false as a result.
     * 
     * @param vertexData    An object of type V that represents the desired 
     *                      vertex.
     * @return true if the provided vertex is present in the current graph;
     *         false otherwise.
     */
    public boolean isEWDGraphVertex(V vertexData) {
        if (vertexData == null)
            throw new IllegalArgumentException("Parameter is null");
        return adjacencyMap.containsKey(vertexData);
    }

    /**
     * Retrieves and returns all of the vertices that are present in the 
     * current EWDGraph object through the processing of the adjacencyMap
     * field, which stores the vertices and edges associated with the current
     * object. 
     * 
     * @return An object that implements the Collection interface and contains
     *         the vertices that are present in the current EWDGraph object.
     */
    public Collection<V> getEWDGraphVertices() {
        // Instantiate a new Collection that is independent of the current 
        // graph object.
        Collection<V> col = new HashSet<>(adjacencyMap.keySet());
        return col;
    }

    /**
     * Adds the desired edge with the specified weight from the given
     * source vertex to the destination vertex. If either vertex parameter
     * is non-existent in the current data structure, the non-existent
     * vertex is added and the operations proceed. If there is already
     * an edge from the source to the destination, then the weight is updated
     * accordingly. Otherwise, the new edge is added and true is returned. 
     * A value of false is returned in the case that the weight is invalid
     * and therefore the edge was not added to the data structure.
     * 
     * @param srcVert   An object of type V representing the source vertex
     *                  that the edge is associated with. 
     * @param destVert  An object of type V representing the destination vertex
     *                  that the edge is associated with. 
     * @param weight    A primitive integer representing the weight of the
     *                  desired edge.
     * @return true if the edge was successfully added; false otherwise.
     */
    public boolean newEWDGraphEdge(V srcVert, V destVert, int weight) {
        if (srcVert == null || destVert == null)
            throw new IllegalArgumentException("Parameter is null");
        boolean added = true;
        // The weight is invalid or there is an attempt to map an edge
        // to the same vertex
        if (weight <= 0 || srcVert.equals(destVert)) {
            added = false;
        } else {
            // If either the source or destination vertices are not added,
            // then add them respectively
            if (!adjacencyMap.containsKey(srcVert)) {
                this.newEWDGraphVertex(srcVert);
            }
            if (!adjacencyMap.containsKey(destVert)) {
                this.newEWDGraphVertex(destVert);
            }
            // Add the neighboring vertex and the associated weight to the
            // value in the HashMap that corresponds to the source vertex 
            adjacencyMap.get(srcVert).put(destVert, weight);
        }
        return added;
    }

    /**
     * Removes the edge going from source vertex to the destination vertex and
     * returns true. If either srcVert or destVert are not present 
     * in the current object or if there is no edge between either parameter,
     * then false is returned. 
     * 
     * @param srcVert   An object of type V representing the source vertex
     *                  that the edge is associated with. 
     * @param destVert  An object of type V representing the destination vertex
     *                  that the edge is associated with. 
     * @return true if the edge from the specified source vertex to the
     *         specified destination vertex was removed; false otherwise.
     */
    public boolean removeEWDGraphEdge(V srcVert, V destVert) {
        if (srcVert == null || destVert == null)
            throw new IllegalArgumentException("Parameter is null");
        boolean removed = true;
        if (!adjacencyMap.containsKey(srcVert) || 
            !adjacencyMap.containsKey(destVert) || 
            !adjacencyMap.get(srcVert).containsKey(destVert)) {
            removed = false;
        } else {
            adjacencyMap.get(srcVert).remove(destVert);
        }
        return removed;
    }

    /**
     * Retrieves and returns an object that contains all neighbors of the
     * vertex specified by the vertexData parameter, which include the vertices
     * that the specified vertex directs an edge to. If the specified vertex
     * is not present in the current EWDGraph object, then null is returned.
     * 
     * @param vertexData    An object of type V that represents the desired 
     *                      vertex.
     * @return An object containing the neighbors of the specified vertex; 
     *         null if the vertex does not exist in the data structure.
     */
    public Collection<V> getNeighborsOfVertex(V vertexData) {
        if (vertexData == null)
            throw new IllegalArgumentException("Parameter is null");
        if (!adjacencyMap.containsKey(vertexData)) {
            Collection<V> col = new HashSet<>();
            return col;
        }
        // Instantiate a new Collection that is independent of the current 
        // graph object.
        Collection<V> col = new HashSet<>(
                adjacencyMap.get(vertexData).keySet());
        return col;
    }

}