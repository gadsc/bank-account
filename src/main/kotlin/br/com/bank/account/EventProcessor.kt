package br.com.bank.account

import violation.OperationViolation

object EventProcessor {
    fun process(it: Any): OperationResult =
        if (it is AccountRequest) {
            AccountRepository.createAccount(Account.from(it))
        } else {
            val transaction = Transaction.from(it as TransactionRequest)
            val account: OperationResult = transaction.commit(AccountRepository.find())
            account?.account?.let { acc -> AccountRepository.updateActiveAccount(acc) }

            println(account)

            account
        }

}