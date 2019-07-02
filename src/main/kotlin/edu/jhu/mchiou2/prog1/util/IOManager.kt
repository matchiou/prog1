package edu.jhu.mchiou2.prog1.util

import java.io.File

/**
 * Utility class to handle I/O related tasks.
 */
class IOManager {
    /**
     * Append the [content] to the file at [filePath].
     * If the file is not there, it will be created.
     *
     * @param filePath path to the output file
     * @param content string to write to the file
     */
    fun appendToFile(filePath: String, content: String) {
        val file = File(filePath)
        file.createNewFile()
        file.appendText(content)
    }
}