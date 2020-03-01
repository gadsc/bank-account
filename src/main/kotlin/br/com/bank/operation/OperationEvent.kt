package br.com.bank.operation

interface OperationEvent {
    fun toOperation(): Operation
}