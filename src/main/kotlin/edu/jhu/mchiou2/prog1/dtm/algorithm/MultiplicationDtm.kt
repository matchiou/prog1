package edu.jhu.mchiou2.prog1.dtm.algorithm

import edu.jhu.mchiou2.prog1.dtm.model.*
import edu.jhu.mchiou2.prog1.util.IOManager

class MultiplicationDtm(
    ioManager: IOManager
) : Dtm(
    allSymbols,
    standardStates,
    terminationStates,
    startingState,
    ioManager
)  {
    override val defaultOutputLocation: String = "../../../../../multiplication_dtm_result.txt"

    companion object {
        private val symbolUnary = Symbol('0') // unary

        private val symbolBlank = Symbol('#') // blank

        private val symbolMultiply = Symbol('*') // multiply sign

        private val symbolRightRead = Symbol('X') // denote index already read on the right hand side
        private val symbolLeftRead = Symbol('Y') // denote index already read on the left hand side

        private val allSymbols = listOf(symbolUnary, symbolMultiply, symbolBlank, symbolRightRead, symbolLeftRead)

        private val stateMultiplicationCompleted = TerminationState(
            "stateMultiplicationCompleted",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMultiply to StateTransitionResult(null, symbolMultiply, Direction.HALT),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.HALT)
            )
        )

        private val terminationStates = listOf(stateMultiplicationCompleted)

        private val stateQ11 = StandardState(
            "stateQ11",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
//                symbolMultiply to StateTransitionResult(stateQ3, symbolMultiply, Direction.FORWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.HALT),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.HALT),
                symbolLeftRead to StateTransitionResult(null, symbolUnary, Direction.FORWARD)
            )
        )

        private val stateQ10 = StandardState(
            "stateQ10",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
//                symbolMultiply to StateTransitionResult(stateQ5, symbolMultiply, Direction.BACKWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.BACKWARD),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.BACKWARD),
                symbolLeftRead to StateTransitionResult(null, symbolLeftRead, Direction.HALT)
            )
        )

        private val stateQ9 = StandardState(
            "stateQ9",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMultiply to StateTransitionResult(stateQ10, symbolMultiply, Direction.BACKWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.BACKWARD),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.HALT),
                symbolLeftRead to StateTransitionResult(null, symbolLeftRead, Direction.HALT)
            )
        )

        private val stateQ8 = StandardState(
            "stateQ8",
            mutableMapOf(
                symbolBlank to StateTransitionResult(stateQ9, symbolUnary, Direction.BACKWARD),
                symbolMultiply to StateTransitionResult(null, symbolMultiply, Direction.HALT),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.FORWARD),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.HALT),
                symbolLeftRead to StateTransitionResult(null, symbolLeftRead, Direction.HALT)
            )
        )

        private val stateQ7 = StandardState(
            "stateQ7",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMultiply to StateTransitionResult(stateQ8, symbolMultiply, Direction.FORWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.FORWARD),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.FORWARD),
                symbolLeftRead to StateTransitionResult(null, symbolLeftRead, Direction.HALT)
            )
        )

        private val stateQ6 = StandardState(
            "stateQ6",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMultiply to StateTransitionResult(stateQ7, symbolMultiply, Direction.FORWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.HALT),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.HALT),
                symbolLeftRead to StateTransitionResult(null, symbolLeftRead, Direction.FORWARD)
            )
        )

        private val stateQ5 = StandardState(
            "stateQ5",
            mutableMapOf(
                symbolBlank to StateTransitionResult(stateQ11, symbolBlank, Direction.FORWARD),
                symbolMultiply to StateTransitionResult(null, symbolMultiply, Direction.HALT),
                symbolUnary to StateTransitionResult(stateQ6, symbolLeftRead, Direction.FORWARD),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.HALT),
                symbolLeftRead to StateTransitionResult(null, symbolLeftRead, Direction.BACKWARD)
            )
        )

        private val stateQ4 = StandardState(
            "stateQ4",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMultiply to StateTransitionResult(stateQ5, symbolMultiply, Direction.BACKWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.HALT),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.BACKWARD),
                symbolLeftRead to StateTransitionResult(null, symbolLeftRead, Direction.HALT)
            )
        )

        private val stateQ3 = StandardState(
            "stateQ3",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMultiply to StateTransitionResult(stateMultiplicationCompleted, symbolBlank, Direction.FORWARD),
                symbolUnary to StateTransitionResult(stateQ4, symbolRightRead, Direction.BACKWARD),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.FORWARD),
                symbolLeftRead to StateTransitionResult(null, symbolLeftRead, Direction.HALT)
            )
        )

        private val stateQ2 = StandardState(
            "stateQ2",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMultiply to StateTransitionResult(stateQ3, symbolMultiply, Direction.FORWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.BACKWARD),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.HALT),
                symbolLeftRead to StateTransitionResult(null, symbolLeftRead, Direction.HALT)
            )
        )

        private val stateQ1 = StandardState(
            "stateQ1",
            mutableMapOf(
                symbolBlank to StateTransitionResult(stateQ2, symbolMultiply, Direction.BACKWARD),
                symbolMultiply to StateTransitionResult(null, symbolMultiply, Direction.HALT),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.FORWARD),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.HALT),
                symbolLeftRead to StateTransitionResult(null, symbolLeftRead, Direction.HALT)
            )
        )

        private val stateQ0 = StandardState(
            "stateQ0",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMultiply to StateTransitionResult(stateQ1, symbolMultiply, Direction.FORWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.FORWARD),
                symbolRightRead to StateTransitionResult(null, symbolRightRead, Direction.HALT),
                symbolLeftRead to StateTransitionResult(null, symbolLeftRead, Direction.HALT)
            )
        )

        private val standardStates = listOf(
            stateQ0,
            stateQ1,
            stateQ2,
            stateQ3,
            stateQ4,
            stateQ5,
            stateQ6,
            stateQ7,
            stateQ8,
            stateQ9,
            stateQ10,
            stateQ11
        )

        // create starting state and pointing it to stateQ0
        private val startingState = StartingState(stateQ0)
    }

    /**
     * this block gets run right after object initialization.
     * This is to resolve circular dependency issue between state initializations
     */
    init {
        stateQ11.operations[symbolMultiply] = StateTransitionResult(stateQ3, symbolMultiply, Direction.FORWARD)

        stateQ10.operations[symbolMultiply] = StateTransitionResult(stateQ5, symbolMultiply, Direction.BACKWARD)
    }

    override fun getSymbolFromChar(currentChar: Char) =
        when (currentChar) {
            '*' -> symbolMultiply
            '0' -> symbolUnary
            '#' -> symbolBlank
            'X' -> symbolRightRead
            'Y' -> symbolLeftRead
            else -> throw IllegalStateException("Unrecognized symbol $currentChar in tape.")
        }
}