package edu.jhu.mchiou2.prog1.dtm.algorithm

import edu.jhu.mchiou2.prog1.dtm.model.*
import edu.jhu.mchiou2.prog1.util.IOManager

class SubtractionDtm(
    ioManager: IOManager
) : Dtm(
    allSymbols,
    standardStates,
    terminationStates,
    startingState,
    ioManager
) {
    override val defaultOutputLocation: String = "../../../../../subtraction_dtm_result.txt"

    companion object {
        private val symbolUnary = Symbol('0') // unary

        private val symbolBlank = Symbol('#') // blank

        private val symbolMinus = Symbol('-') // minus sign

        private val symbolRead = Symbol('X') // read sign

        private val allSymbols = listOf(symbolUnary, symbolMinus, symbolBlank, symbolRead)

        private val stateSubtractionCompleted = TerminationState(
            "stateSubtractionCompleted",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMinus to StateTransitionResult(null, symbolMinus, Direction.HALT),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.HALT),
                symbolRead to StateTransitionResult(null, symbolRead, Direction.HALT)
            )
        )

        private val terminationStates = listOf(stateSubtractionCompleted)

        private val stateQ5 = StandardState(
            "stateQ5",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMinus to StateTransitionResult(stateSubtractionCompleted, symbolBlank, Direction.BACKWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.BACKWARD),
                symbolRead to StateTransitionResult(null, symbolBlank, Direction.BACKWARD)
            )
        )

        private val stateQ4 = StandardState(
            "stateQ4",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMinus to StateTransitionResult(null, symbolMinus, Direction.HALT),
//                symbolUnary to StateTransitionResult(stateQ0, symbolBlank, Direction.FORWARD),
                symbolRead to StateTransitionResult(null, symbolRead, Direction.HALT)
            )
        )

        private val stateQ3 = StandardState(
            "stateQ3",
            mutableMapOf(
                symbolBlank to StateTransitionResult(stateQ4, symbolBlank, Direction.FORWARD),
                symbolMinus to StateTransitionResult(null, symbolMinus, Direction.HALT),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.BACKWARD),
                symbolRead to StateTransitionResult(null, symbolRead, Direction.HALT)
            )
        )

        private val stateQ2 = StandardState(
            "stateQ2",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMinus to StateTransitionResult(stateQ3, symbolMinus, Direction.BACKWARD),
                symbolUnary to StateTransitionResult(null, symbolRead, Direction.HALT),
                symbolRead to StateTransitionResult(null, symbolRead, Direction.BACKWARD)
            )
        )

        private val stateQ1 = StandardState(
            "stateQ1",
            mutableMapOf(
                symbolBlank to StateTransitionResult(stateQ5, symbolBlank, Direction.BACKWARD),
                symbolMinus to StateTransitionResult(null, symbolMinus, Direction.HALT),
                symbolUnary to StateTransitionResult(stateQ2, symbolRead, Direction.BACKWARD),
                symbolRead to StateTransitionResult(null, symbolRead, Direction.FORWARD)
            )
        )

        private val stateQ0 = StandardState(
            "stateQ0",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolMinus to StateTransitionResult(stateQ1, symbolMinus, Direction.FORWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.FORWARD),
                symbolRead to StateTransitionResult(null, symbolRead, Direction.HALT)
            )
        )

        private val standardStates = listOf(stateQ0, stateQ1, stateQ2, stateQ3, stateQ4, stateQ5)

        // create starting state and pointing it to stateQ0
        private val startingState = StartingState(stateQ0)
    }

    /**
     * this block gets run right after object initialization.
     * This is to resolve circular dependency issue between state initializations
     */
    init {
        stateQ4.operations[symbolUnary] = StateTransitionResult(stateQ0, symbolBlank, Direction.FORWARD)
    }

    override fun getSymbolFromChar(currentChar: Char) =
        when (currentChar) {
            '-' -> symbolMinus
            '0' -> symbolUnary
            '#' -> symbolBlank
            'X' -> symbolRead
            else -> throw IllegalStateException("Unrecognized symbol $currentChar in tape.")
        }
}