package edu.jhu.mchiou2.prog1.dtm.algorithm

import edu.jhu.mchiou2.prog1.dtm.model.*
import edu.jhu.mchiou2.prog1.util.IOManager

class AdditionDtm(
    ioManager: IOManager
) : Dtm(
    allSymbols,
    standardStates,
    terminationStates,
    startingState,
    ioManager
) {
    override val defaultOutputLocation: String = "../../../../../addition_dtm_result.txt"

    companion object {
        private val symbolUnary = Symbol('0') // unary

        private val symbolBlank = Symbol('#') // blank

        private val symbolPlus = Symbol('+') // plus sign

        private val allSymbols = listOf(symbolUnary, symbolPlus, symbolBlank)

        private val stateAdditionCompleted = TerminationState(
            "stateAdditionCompleted",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.HALT),
                symbolPlus to StateTransitionResult(null, symbolPlus, Direction.HALT),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.HALT)
            )
        )

        private val terminationStates = listOf(stateAdditionCompleted)

        private val stateQ2 = StandardState(
            "stateQ2",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.BACKWARD),
                symbolPlus to StateTransitionResult(null, symbolPlus, Direction.BACKWARD),
                symbolUnary to StateTransitionResult(stateAdditionCompleted, symbolBlank, Direction.HALT)
            )
        )

        private val stateQ1 = StandardState(
            "stateQ1",
            mutableMapOf(
                symbolBlank to StateTransitionResult(stateQ2, symbolBlank, Direction.BACKWARD),
                symbolPlus to StateTransitionResult(null, symbolPlus, Direction.FORWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.FORWARD)
            )
        )

        private val stateQ0 = StandardState(
            "stateQ0",
            mutableMapOf(
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.FORWARD),
                symbolPlus to StateTransitionResult(stateQ1, symbolUnary, Direction.FORWARD),
                symbolUnary to StateTransitionResult(null, symbolUnary, Direction.FORWARD)
            )
        )

        private val standardStates = listOf(stateQ0, stateQ1, stateQ2)

        // create starting state and pointing it to stateQ0
        private val startingState = StartingState(stateQ0)
    }

    override fun getSymbolFromChar(currentChar: Char) =
        when (currentChar) {
            '+' -> symbolPlus
            '0' -> symbolUnary
            '#' -> symbolBlank
            else -> throw IllegalStateException("Unrecognized symbol $currentChar in tape.")
        }
}
