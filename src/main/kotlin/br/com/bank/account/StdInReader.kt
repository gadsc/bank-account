package br.com.bank.account

class StdInReader: Reader {
    override tailrec fun recursiveRead(events: List<String>, exitCode: String): List<String> {
        val line = readLine().orEmpty()

        return if (line == exitCode) {
            events
        } else {
            recursiveRead(events = events + line, exitCode = exitCode)
        }
    }
}

interface Reader {
    fun recursiveRead(events: List<String> = emptyList(), exitCode: String = ""): List<String>
}