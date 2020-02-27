package br.com.bank.account

object EventProcessorFactory {
    fun process(it: BankEvent): OperationResult =
        if (it is AccountRequest) {
            AccountEventProcessor().process(Account.from(it))
        } else {
            TransactionEventProcessor().process(Transaction.from(it as TransactionRequest))
        }
}