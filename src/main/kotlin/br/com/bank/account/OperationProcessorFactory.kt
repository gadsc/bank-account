package br.com.bank.account

object OperationProcessorFactory {
    fun resolve(it: OperationEvent): OperationProcessor = when (it) {
        is AccountOperationEvent -> AccountOperationProcessor()
        is TransactionOperationEvent -> TransactionOperationProcessor()
        else -> throw RuntimeException("Not expected message")
    }
}