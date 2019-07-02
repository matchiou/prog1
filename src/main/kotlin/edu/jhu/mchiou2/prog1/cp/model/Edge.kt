package edu.jhu.mchiou2.prog1.cp.model

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @property first the first [Node]
 * @property second the second [Node]
 * @property distance The distance as double between two nodes.
 */
data class Edge(
    val first: Node?,
    val second: Node?,
    val distance: Double = distanceBetween(first!!, second!!)
) {
    companion object {
        /**
         * Helper to calculate the Euclidean distance between two nodes.
         * @param first the first node
         * @param second the second node
         * @return the Euclidean distance between the two nodes
         */
        private fun distanceBetween(first: Node, second: Node): Double {
            val xDiff = first.xCoordinate - second.xCoordinate
            val yDiff = first.yCoordinate - second.yCoordinate

            val xDiffSquare = xDiff.toDouble().pow(2)
            val yDiffSquare = yDiff.toDouble().pow(2)

            return sqrt(xDiffSquare + yDiffSquare)
        }
    }
}