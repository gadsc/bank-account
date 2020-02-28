package br.com.bank.account

interface OperationProcessor {
    fun process(operation: Operation): OperationResult
}