package br.com.bank.operation.consumer.stdin

import br.com.bank.infra.Reader

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
