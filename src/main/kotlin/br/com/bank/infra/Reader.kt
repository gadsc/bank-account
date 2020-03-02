package br.com.bank.infra

interface Reader {
    fun recursiveRead(events: List<String> = emptyList(), exitCode: String = ""): List<String>
}