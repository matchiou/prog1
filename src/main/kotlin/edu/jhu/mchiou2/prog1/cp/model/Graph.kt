package edu.jhu.mchiou2.prog1.cp.model

import java.io.File
import kotlin.random.Random

/**
 * POJO of the input of the closest pair problem.
 *
 * @property nodes A list of [Node] in the graph.
 */
data class Graph(
    val nodes: List<Node>
) {
    /**
     * This is Kotlin-way of doing static methods/variables
     */
    companion object {
        private const val COMMA_SEPARATOR = ","
        private const val MAX_COORDINATE = Int.MAX_VALUE
        private const val MIN_COORDINATE = 0
        private const val DEFAULT_RANDOM_SEED = 10

        /**
         * Helper function to generate a Graph from a file.
         * It's expecting each line of the file to be x-coordinate of a node,
         * follows by a comma, and follows by y-coordinate of a node.
         *
         * @param filePath file path to the source file.
         */
        fun createFromFile(filePath: String): Graph {
            val nodes = mutableListOf<Node>()
            val file = File(filePath)
            file.forEachLine {
                val splitByComma = it.split(COMMA_SEPARATOR)
                val node = Node(
                    xCoordinate = splitByComma[0].toInt(),
                    yCoordinate = splitByComma[1].toInt()
                )
                nodes.add(node)
            }

            return Graph(nodes.toList())
        }

        /**
         * Helper function to generate a Graph randomly.
         * The random function is seeded so it can generate the same Graph
         * if [numberOfNodes], [randomSeed], [maxCoordinate] are the same.
         *
         * @param numberOfNodes number of nodes to generate in the graph
         * @param randomSeed the seed to use for the random function
         * @param maxCoordinate the max integer for the coordinate value.
         */
        fun generateRandomly(
            numberOfNodes: Int,
            randomSeed: Int = DEFAULT_RANDOM_SEED
        ): Graph {
            val nodes = mutableListOf<Node>()
            val random = Random(randomSeed)
            for (counter in 0 until numberOfNodes) {
                val randomXCoord = random.nextInt(MIN_COORDINATE, MAX_COORDINATE)
                val randomYCoord = random.nextInt(MIN_COORDINATE, MAX_COORDINATE)
                val node = Node(
                    xCoordinate = randomXCoord,
                    yCoordinate = randomYCoord
                )
                nodes.add(node)
            }

            return Graph(nodes.toList())
        }
    }
}