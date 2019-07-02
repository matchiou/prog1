package edu.jhu.mchiou2.prog1.dtm.algorithm

import edu.jhu.mchiou2.prog1.dtm.model.*
import edu.jhu.mchiou2.prog1.util.IOManager

/**
 * This Dtm implementation follows Module 3's Dtm transition table.
 *
 * All the states, symbols, and transitions are defined in this class.
 * The actually implementation of the execution is in [Dtm].
 */
class StandardDtm(
    ioManager: IOManager
) : Dtm(
    allSymbols,
    standardStates,
    terminationStates,
    startingState,
    ioManager
) {
    override val defaultOutputLocation: String = "../../../../../standard_dtm_result.txt"

    companion object {

        // create symbols
        private val symbolZero = Symbol('0') // 0
        private val symbolOne = Symbol('1') // 1
        private val symbolBlank = Symbol('#') // blank
        private val allSymbols = listOf(symbolZero, symbolOne, symbolBlank)

        // setup termination states
        // null means there's no state changes
        private val terminationStateN = TerminationState(
            "terminationStateN",
            mutableMapOf(
                symbolZero to StateTransitionResult(null, symbolBlank, Direction.BACKWARD),
                symbolOne to StateTransitionResult(null, symbolBlank, Direction.BACKWARD),
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.BACKWARD)
            )
        )

        private val terminationStateY = TerminationState(
            "terminationStateY",
            mutableMapOf(
                symbolZero to StateTransitionResult(null, symbolBlank, Direction.BACKWARD),
                symbolOne to StateTransitionResult(null, symbolBlank, Direction.BACKWARD),
                symbolBlank to StateTransitionResult(null, symbolBlank, Direction.BACKWARD)
            )
        )

        private val terminationStates = listOf(terminationStateN, terminationStateY)

        // setup states. Null means there's no state changes
        private val stateQ3 = StandardState(
            "stateQ3",
            mutableMapOf(
                symbolZero to StateTransitionResult(terminationStateN, symbolBlank, Direction.BACKWARD),
                symbolOne to StateTransitionResult(terminationStateN, symbolBlank, Direction.BACKWARD),
                symbolBlank to StateTransitionResult(terminationStateN, symbolBlank, Direction.BACKWARD)
            )
        )

        private val stateQ2 = StandardState(
            "stateQ2",
            mutableMapOf(
                symbolZero to StateTransitionResult(terminationStateY, symbolBlank, Direction.BACKWARD),
                symbolOne to StateTransitionResult(terminationStateN, symbolBlank, Direction.BACKWARD),
                symbolBlank to StateTransitionResult(terminationStateN, symbolBlank, Direction.BACKWARD)
            )
        )

        private val stateQ1 = StandardState(
            "stateQ1",
            mutableMapOf(
                symbolZero to StateTransitionResult(stateQ2, symbolBlank, Direction.BACKWARD),
                symbolOne to StateTransitionResult(stateQ3, symbolBlank, Direction.BACKWARD),
                symbolBlank to StateTransitionResult(terminationStateN, symbolBlank, Direction.BACKWARD)
            )
        )

        private val stateQ0 = StandardState(
            "stateQ0",
            mutableMapOf(
                symbolZero to StateTransitionResult(null, symbolZero, Direction.FORWARD),
                symbolOne to StateTransitionResult(null, symbolOne, Direction.FORWARD),
                symbolBlank to StateTransitionResult(stateQ1, symbolBlank, Direction.BACKWARD)
            )
        )

        private val standardStates = listOf(stateQ0, stateQ1, stateQ2, stateQ3)

        // create starting state and pointing it to stateQ0
        private val startingState = StartingState(stateQ0)
    }

    override fun getSymbolFromChar(currentChar: Char) =
        when (currentChar) {
            '1' -> symbolOne
            '0' -> symbolZero
            '#' -> symbolBlank
            else -> throw IllegalStateException("Unrecognized symbol $currentChar in tape.")
        }
}