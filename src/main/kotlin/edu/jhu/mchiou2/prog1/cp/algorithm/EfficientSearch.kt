package edu.jhu.mchiou2.prog1.cp.algorithm

import edu.jhu.mchiou2.prog1.cp.model.Edge
import edu.jhu.mchiou2.prog1.cp.model.Graph
import edu.jhu.mchiou2.prog1.cp.model.Node
import edu.jhu.mchiou2.prog1.util.IOManager
import kotlin.math.abs

class EfficientSearch(
    ioManager: IOManager,
    private val bruteForceSearch: BruteForceSearch
) : ClosestPairSearchAlgorithm(ioManager) {


    override
    fun searchMultiple(
        graph: Graph,
        numberOfEdges: Int
    ): List<Edge> {
TODO()
    }

    override fun search(graph: Graph): Edge {
        val sortedX = graph.nodes.sortedBy { it.xCoordinate }
        val sortedY = graph.nodes.sortedBy { it.yCoordinate }
        return closest(sortedX, sortedY)
    }

    private fun closest(
        sortedX: List<Node>,
        sortedY: List<Node>
    ): Edge {
        val size = sortedX.size

        // handle recursive case
        // this is not pretty but the best we can do instead of adding two if-statements before recursion calls
        if (size < 2) {
            return Edge(
                null,
                null,
                Double.POSITIVE_INFINITY
            )
        }

        if (size <= 3) {
            return bruteForceSearch
                .search(
                    Graph(sortedX)
                )
        }

        val midIndex = size / 2
        val midNode = sortedX[midIndex]

        val sortedYPartitionByX = sortedY.partition { it.xCoordinate <= midNode.xCoordinate }

        // For clarity purpose
        // Kotlin partition function split the list into a Pair of Lists
        // where the first element of the pair contains the objects which yields true for the filtering
        val leftHandSideSortedY = sortedYPartitionByX.first
        val rightHandSideSortedY = sortedYPartitionByX.second

        // find shortest edge by X
        val shortestEdgeLeftHandSide = closest(sortedX.subList(0, midIndex + 1), leftHandSideSortedY)
        val shortestEdgeRightHandSide = closest(sortedX.subList(midIndex + 1, sortedX.size - 1), rightHandSideSortedY)
        val shortestDistanceByX = minOf(shortestEdgeLeftHandSide, shortestEdgeRightHandSide, compareBy { it.distance })

        // finding the group of nodes around mid node on both side
        val nodesCloseToMidNode =
            sortedY.filter { abs(it.xCoordinate - midNode.xCoordinate) < shortestDistanceByX.distance }

        // find the shortest edge by Y
        val shortestDistanceByY = closestByY(nodesCloseToMidNode, shortestDistanceByX)

        // pick the shorter edge between X and Y results
        return minOf(shortestDistanceByY, shortestDistanceByX, compareBy { it.distance })
    }

    private fun closestByY(nodes: List<Node>, shortestEdgeByX: Edge): Edge {
        var shortestEdgeByY = shortestEdgeByX
        for (outerIndex in 0 until nodes.size) {
            for (innerIndex in outerIndex + 1 until nodes.size) {
                if ((nodes[innerIndex].yCoordinate - nodes[outerIndex].yCoordinate) >= shortestEdgeByY.distance) {
                    break
                }
                val edge = Edge(nodes[outerIndex], nodes[innerIndex])
                if (edge.distance < shortestEdgeByY.distance) {
                    shortestEdgeByY = edge
                }
            }
        }
        return shortestEdgeByY
    }
}