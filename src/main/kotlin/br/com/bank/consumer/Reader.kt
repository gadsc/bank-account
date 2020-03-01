package br.com.bank.consumer

interface Reader {
    fun recursiveRead(events: List<String> = emptyList(), exitCode: String = ""): List<String>
}