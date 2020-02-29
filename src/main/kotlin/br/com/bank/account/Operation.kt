package br.com.bank.account

interface Operation {
    fun getIdentifier(): OperationIdentifier
}