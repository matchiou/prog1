package edu.jhu.mchiou2.prog1.cp.algorithm

import edu.jhu.mchiou2.prog1.cp.model.Edge
import edu.jhu.mchiou2.prog1.cp.model.Graph
import edu.jhu.mchiou2.prog1.util.IOManager

/**
 * Create a list of Edges between all nodes then perform search.
 * This class has a worst case complexity of O(n^n).
 */
class BruteForceSearch(ioManager: IOManager): ClosestPairSearchAlgorithm(ioManager) {

    /**
     * Search the shortest edge within the graph by checking all edges sequentially.
     */
    override fun search(graph: Graph): Edge {
        var min = Double.POSITIVE_INFINITY
        var shortestEdge: Edge? = null

        val nodes = graph.nodes
        val size = nodes.size

        for (outerIndex in 0 until size) {
            for (innerIndex in outerIndex + 1 until size) {
                val edge = Edge(nodes[outerIndex], nodes[innerIndex])
                if (edge.distance < min) {
                    min = edge.distance
                    shortestEdge = edge
                }
            }
        }

        return shortestEdge!!
    }

    /**
     * Create a list of Edges between all nodes, sort them by distance, then pick
     * the first [numberOfEdges] edges.
     */
    override fun searchMultiple(graph: Graph, numberOfEdges: Int): List<Edge> {
        return sortAllEdges(graph).subList(0, numberOfEdges)
    }

    fun sortAllEdges(graph: Graph): List<Edge> {
        val nodes = graph.nodes
        val size = nodes.size
        val edges = mutableListOf<Edge>()

        for (outerIndex in 0 until size) {
            for (innerIndex in outerIndex + 1 until size) {
                val edge = Edge(nodes[outerIndex], nodes[innerIndex])
                edges.add(edge)
            }
        }
        return edges.sortedBy { it.distance }
    }
}
