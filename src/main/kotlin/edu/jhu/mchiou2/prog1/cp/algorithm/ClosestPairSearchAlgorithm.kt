package edu.jhu.mchiou2.prog1.cp.algorithm

import edu.jhu.mchiou2.prog1.cp.model.Edge
import edu.jhu.mchiou2.prog1.cp.model.Graph
import edu.jhu.mchiou2.prog1.util.IOManager

/**
 * All closest pair algorithm needs to implement this interface.
 */
abstract class ClosestPairSearchAlgorithm(
    private val ioManager: IOManager
) {
    /**
     * Search for the shortest edge between two nodes.
     * @param graph the input 2D plane
     * @return an Edge between the the closest pair.
     */
    abstract fun search(graph: Graph): Edge

    /**
     * Search for the shortest first [numberOfEdges] edges.
     * @param graph the input 2D plane
     * @param numberOfEdges number of edges to return
     * @return an Edge between the the closest pair.
     */
    abstract fun searchMultiple(graph: Graph, numberOfEdges: Int): List<Edge>
}