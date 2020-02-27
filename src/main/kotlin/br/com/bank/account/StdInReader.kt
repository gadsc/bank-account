package br.com.bank.account

object StdInReader {
    tailrec fun inputLoopRec(events: List<String> = emptyList(), exitCode: String = ""): List<String> {
        val line = readLine().orEmpty()

        return if (line == exitCode) {
            events
        } else {
            inputLoopRec(events = events + line, exitCode = exitCode)
        }
    }
}