package edu.jhu.mchiou2.prog1.dtm.algorithm

import edu.jhu.mchiou2.prog1.dtm.model.*
import edu.jhu.mchiou2.prog1.util.IOManager
import org.slf4j.LoggerFactory

/**
 * Foundation for Dtm classes. All Dtm needs to extend from this class.
 * This class implements the execution logic (update heads, run transition, etc) of all Dtm subclasses.
 *
 * It always log the information (if the log level is info or lower) about states and symbols.
 *
 * @property symbols a list of all the available symbols in the Dtm. (Gamma)
 * @property states a list of all the possible states in the Dtm.
 * @property terminationStates a list of termination states in the Dtm.
 * @property startingState the starting state of the Dtm.
 * @property ioManager helper to manage outputting result.
 */
abstract class Dtm(
    private val symbols: List<Symbol>,
    private val states: List<StandardState>,
    private val terminationStates: List<TerminationState>,
    private val startingState: StartingState,
    private val ioManager: IOManager
) {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    abstract val defaultOutputLocation: String

    /**
     * Log the value of the symbols
     *
     * Intentionally left as public per assignment requirement
     */
    fun describeSymbols(){
        logger.info("all symbols of ${this::class.simpleName} are: ${symbols.map { it.value }}")
    }

    /**
     * Log the name of [startingState], [terminationStates], and [states]
     *
     * Intentionally left as public per assignment requirement
     */
    fun describeAllStates() {
        logger.info("starting State: ${startingState.name}\n terminating states: ${terminationStates.map { it.name }}\n other states: ${states.map { it.name }}")
    }

    /**
     * Execute the pre-defined turing machine.
     *
     * @param input a string representing the tape
     * @param startingIndex the starting position of the Dtm on the tape
     * @return a string representing the final status of the tape
     */
    private fun execute(input: String, startingIndex: Int): String {
        val outputString = input.toMutableList()
        var currentState: State = startingState.state
        var dtmHead = startingIndex
        logger.info("Starting Standard DTM execution...")
        logger.info("Starting tape status: $outputString")
        logger.info("Starting state is ${currentState.name}.")
        while ((currentState is StandardState)) {
            val currentChar = outputString[dtmHead]
            logger.info("Tape value read is $currentChar")

            val currentSymbol = getSymbolFromChar(currentChar)

            val operation = currentState.operations
            val operationResult = operation[currentSymbol]

            check(operationResult != null)

            outputString[dtmHead] = operationResult.overwriteSymbol.value
            logger.trace("Overwrite the current value to ${operationResult.overwriteSymbol.value}")

            dtmHead += operationResult.direction.value
            logger.info("Head is at index: $dtmHead")
            logger.info("Move in the direction of ${operationResult.direction}")

            if (operationResult.outputState != null) {
                currentState = operationResult.outputState
            }
            logger.info("Current state after the move is ${currentState.name}")
            logger.info("Current tape status: $outputString")
        }

        return outputString.toString()
    }

    /**
     * Run the Dtm.
     *
     * @param input a string representing the tape
     * @param startingIndex the starting position of the Dtm on the tape
     */
    fun run(input: String, startingIndex: Int, outputLocation: String = defaultOutputLocation): String {
        describeSymbols()
        describeAllStates()
        val result = execute(input, startingIndex)
        handleOutput(input.length, "input: ${input.toList()}, run output: $result\n", outputLocation)
        return result
    }

    /**
     * Helper to output to file if tape is longer than 30.
     * Otherwise, output to console.
     */
    private fun handleOutput(inputSize: Int, output: String, outputLocation: String) {
        if (inputSize > 30) {
            ioManager.appendToFile(filePath = outputLocation, content = output)
        } else {
            logger.info(output)
        }
    }

    /**
     * Create an easy mapping between character and symbol
     *
     * @param currentChar the current character read from the tape
     * @return a [Symbol] object that [currentChar] represents
     * @throws IllegalStateException when [currentChar] is not recognized.
     */
    protected abstract fun getSymbolFromChar(currentChar: Char): Symbol
}
