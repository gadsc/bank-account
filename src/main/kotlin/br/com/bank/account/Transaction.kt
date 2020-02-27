package br.com.bank.account

import violation.AccountNotInitializedViolation
import violation.CardNotActiveViolation
import java.time.ZonedDateTime

data class Transaction(val merchant: String, val amount: Long, val time: ZonedDateTime) {
    companion object {
        fun from(transactionRequest: TransactionRequest) = Transaction(
            merchant = transactionRequest.merchant,
            amount = transactionRequest.amount,
            time = transactionRequest.time
        )
    }

    fun commit(account: Account?): OperationResult = when {
        account == null -> OperationResult(
            account = null,
            violations = listOf(AccountNotInitializedViolation())
        )
        !account.activeCard -> OperationResult(
            account = account,
            violations = listOf(CardNotActiveViolation())
        )
        else -> account.commitTransaction(transaction = this)
    }
}