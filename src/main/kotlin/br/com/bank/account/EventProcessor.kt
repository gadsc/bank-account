package br.com.bank.account

object EventProcessor {
    fun process(it: Any): Pair<Account?, List<Violation>> =
        if (it is AccountRequest) {
            AccountRepository.createAccount(Account.from(it))
        } else {
            val transaction = Transaction.from(it as TransactionRequest)
            val account: Pair<Account?, List<Violation>> = transaction.commit(AccountRepository.find())
            account?.first?.let { acc -> AccountRepository.updateActiveAccount(acc) }

            account
        }

}