package edu.jhu.mchiou2.prog1.dtm

import edu.jhu.mchiou2.prog1.dtm.algorithm.AdditionDtm
import edu.jhu.mchiou2.prog1.dtm.algorithm.MultiplicationDtm
import edu.jhu.mchiou2.prog1.dtm.algorithm.StandardDtm
import edu.jhu.mchiou2.prog1.dtm.algorithm.SubtractionDtm
import edu.jhu.mchiou2.prog1.util.IOManager

fun main(args: Array<String>) {
    val ioManager = IOManager()
    val standardDtm = StandardDtm(ioManager)
    val additionDtm = AdditionDtm(ioManager)
    val subtractionDtm = SubtractionDtm(ioManager)
    val multiplicationDtm = MultiplicationDtm(ioManager)

}

fun String.fromBinaryToUnary(): String {
    val decimal = this.toInt(2)
    return "0".repeat(decimal)
}

fun String.padFrontEndWithBlank(): String {
    return "#${this}#"
}