package br.com.bank.account

class TransactionOperationProcessor : OperationProcessor {
    override fun process(operation: Operation): OperationResult {
        val account: OperationResult = (operation as Transaction).commit2(AccountRepository.find())
        account?.account?.let { acc -> AccountRepository.updateActiveAccount(acc) }

        println(account)

        return account
    }
}
