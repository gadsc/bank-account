package br.com.bank.account

import violation.AccountNotInitializedViolation
import violation.CardNotActiveViolation
import violation.OperationValidations
import java.time.ZonedDateTime

data class Transaction(val merchant: String, val amount: Long, val time: ZonedDateTime) : Operation {
    companion object {
        fun from(transactionOperationEvent: TransactionOperationEvent) = Transaction(
                merchant = transactionOperationEvent.merchant,
                amount = transactionOperationEvent.amount,
                time = transactionOperationEvent.time
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

    fun commit2(account: Account?): OperationResult {
        val violations = OperationValidations.TRANSACTION_INPUT.mapNotNull { it.violationFor(account) }

        return violations.let {
            if (it.isEmpty()) account!!.commitTransaction(transaction = this@Transaction) else OperationResult(
                    account = account,
                    violations = it
            )
        }
    }

}