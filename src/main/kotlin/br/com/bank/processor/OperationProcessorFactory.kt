package br.com.bank.processor

import br.com.bank.operation.OperationEvent
import br.com.bank.operation.account.AccountOperationEvent
import br.com.bank.operation.account.AccountOperationProcessor
import br.com.bank.operation.transaction.TransactionOperationEvent
import br.com.bank.operation.transaction.TransactionOperationProcessor

object OperationProcessorFactory {
    fun resolve(it: OperationEvent): OperationProcessor = when (it) {
        is AccountOperationEvent -> AccountOperationProcessor()
        is TransactionOperationEvent -> TransactionOperationProcessor()
        else -> throw RuntimeException("Not expected message")
    }
}