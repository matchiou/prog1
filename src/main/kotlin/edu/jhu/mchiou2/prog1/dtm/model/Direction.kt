package edu.jhu.mchiou2.prog1.dtm.model

/**
 * Direction of the Deterministic State Machine.
 * This is used for all Dtm implementations.
 *
 * 0 is halting.
 * 1 is moving forward.
 * -1 is moving backward.
 */
enum class Direction(val value: Int) {
    HALT(0),
    FORWARD(1),
    BACKWARD(-1)
}