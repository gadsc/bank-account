package br.com.bank.account

class TransactionEventProcessor : EventProcessor<Transaction?> {
    override fun process(event: Transaction?): OperationResult {
        val account: OperationResult = event!!.commit(AccountRepository.find())
        account?.account?.let { acc -> AccountRepository.updateActiveAccount(acc) }

        println(account)

        return account
    }
}
