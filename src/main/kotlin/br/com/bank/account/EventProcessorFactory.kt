package br.com.bank.account

object EventProcessorFactory {
    fun resolve(it: BankEvent): EventProcessor =
        if (it is AccountRequest) {
            AccountEventProcessor()//.process(Account.from(it))
        } else {
            TransactionEventProcessor()//.process(Transaction.from(it as TransactionRequest))
        }
}