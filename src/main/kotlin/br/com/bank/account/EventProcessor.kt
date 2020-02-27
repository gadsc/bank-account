package br.com.bank.account

interface EventProcessor<T> {
    fun process(event: T): OperationResult
}