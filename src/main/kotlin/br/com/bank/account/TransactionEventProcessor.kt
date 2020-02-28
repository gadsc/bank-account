package br.com.bank.account

class TransactionEventProcessor : EventProcessor {
    override fun process(event: Operation): OperationResult {
        val account: OperationResult = (event as Transaction).commit(AccountRepository.find())
        account?.account?.let { acc -> AccountRepository.updateActiveAccount(acc) }

        println(account)

        return account
    }
}
