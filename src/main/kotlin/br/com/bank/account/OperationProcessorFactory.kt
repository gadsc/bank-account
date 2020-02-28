package br.com.bank.account

object OperationProcessorFactory {
    fun resolve(it: OperationEvent): OperationProcessor =
        if (it is AccountOperationEvent) {
            AccountOperationProcessor()//.process(Account.from(it))
        } else {
            TransactionOperationProcessor()//.process(Transaction.from(it as TransactionRequest))
        }
}