package br.com.bank.account

import java.time.ZonedDateTime

data class Transaction(val merchant: String, val amount: Long, val time: ZonedDateTime) {
    companion object {
        fun from(transactionRequest: TransactionRequest) = Transaction(
            merchant = transactionRequest.merchant,
            amount = transactionRequest.amount,
            time = transactionRequest.time
        )
    }

    fun commit(account: Account?): Pair<Account?, List<Violation>> = when {
        account == null -> Pair(
            first = null,
            second = listOf(Violation(key = "account-not-initialized", message = "Account not initialized"))
        )
        !account.activeCard -> Pair(
            first = account,
            second = listOf(Violation(key = "card-not-active", message = "Card not active"))
        )
        else -> account.commitTransaction(transaction = this)
    }
}