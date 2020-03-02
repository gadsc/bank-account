package br.com.bank.operation.processor

import br.com.bank.operation.OperationEvent
import br.com.bank.operation.account.AccountOperationEvent
import br.com.bank.operation.account.AccountOperationProcessor
import br.com.bank.operation.account.transaction.TransactionOperationEvent
import br.com.bank.operation.account.transaction.TransactionOperationProcessor

object OperationProcessorFactory {
    fun resolve(operationEvent: OperationEvent): OperationProcessor = when (operationEvent) {
        is AccountOperationEvent -> AccountOperationProcessor()
        is TransactionOperationEvent -> TransactionOperationProcessor()
        else -> throw RuntimeException("Not expected message")
    }
}
