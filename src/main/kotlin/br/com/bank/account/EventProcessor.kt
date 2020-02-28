package br.com.bank.account

interface EventProcessor {
    fun process(event: Operation): OperationResult
}