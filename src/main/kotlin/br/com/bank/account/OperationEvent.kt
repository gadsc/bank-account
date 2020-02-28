package br.com.bank.account

interface OperationEvent {
    fun toOperation(): Operation
}