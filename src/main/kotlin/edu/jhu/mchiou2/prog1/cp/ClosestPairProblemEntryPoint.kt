package edu.jhu.mchiou2.prog1.cp

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.default
import edu.jhu.mchiou2.prog1.cp.algorithm.BruteForceSearch
import edu.jhu.mchiou2.prog1.cp.algorithm.EfficientSearch
import edu.jhu.mchiou2.prog1.cp.model.Graph
import edu.jhu.mchiou2.prog1.util.IOManager

fun main(args: Array<String>) {

    val size = 4
    val outputFilePath = "mchiou2_closest_pair_problems_output.txt"

    val ioManager = IOManager()

    val bruteForceSearch = BruteForceSearch(ioManager)
    val efficientSearch = EfficientSearch(ioManager, bruteForceSearch)

    ArgParser(args).parseInto(::ClosestPairArguments).run {
        val data = Graph.generateRandomly(numberOfPoints.value)

        if (mode == ClosestPairAlgorithm.BRUTE_FORCE) {
            if (getMultiple) {
                bruteForceSearch.searchMultiple(data, k)
            } else {
                bruteForceSearch.search(data)
            }
        } else {
            if (getMultiple) {
                efficientSearch.searchMultiple(data, k)
            } else {
                efficientSearch.search(data)
            }
        }
    }
}

class ClosestPairArguments(parser: ArgParser) {
    val numberOfPoints = parser.storing("-p", "--points", help = "Number of points in the 2D plane.") {
        toInt()
    }.addValidator {
        if (value < 2) {
            throw InvalidArgumentException("Number of points must be greater than 2.")
        }
    }

    val mode by parser.mapping(
        "--slow" to ClosestPairAlgorithm.BRUTE_FORCE,
        "--fast" to ClosestPairAlgorithm.EFFICIENT,
        help = "mode of operation"
    )

    val getMultiple by parser.flagging("-m", "--multiple", help = "Get the first k closest pairs of points").default(false)

    val k by parser.storing("-k", "Number of pairs to return if -m is specified") {toInt()}.default(3)
}

enum class ClosestPairAlgorithm {
    BRUTE_FORCE,
    EFFICIENT
}