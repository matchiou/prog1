package edu.jhu.mchiou2.prog1.dtm.model

/**
 * Dtm state
 *
 * @param name name of the state
 */
abstract class State(open val name: String)

/**
 * Transition state of the Dtm
 * @param operations a map that defines the interaction between a state and all symbols. This is basically transition function.
 */
data class StandardState(override val name: String, val operations: MutableMap<Symbol, StateTransitionResult>) : State(name)

/**
 * Termination state of the Dtm.
 *
 * @param operations a map that defines the interaction between a state and all symbols. This is basically transition function.
 */
data class TerminationState(override val name: String, val operations: Map<Symbol, StateTransitionResult>) : State(name)

/**
 * Starting state of the Dtm
 *
 * @param state the starting transition state
 */
data class StartingState(
    val state: StandardState
): State("startingState")

/**
 * POJO to hold the information of a possible action of transition function
 *
 * @param outputState the state Dtm is in after the transition function. Null means there's no change.
 * @param overwriteSymbol the symbol to write on the current index of the tape
 * @param direction direction the head of the Dtm will move towards afterward
 */
data class StateTransitionResult(
    val outputState: State?, // null represents unchanged state
    val overwriteSymbol: Symbol,
    val direction: Direction
)