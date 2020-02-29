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

    fun commit2(account: Account?): OperationResult =
            Account.readyForTransaction(account).let {
                if (it == null) {
                    account!!.commitTransaction(transaction = this@Transaction)
                } else OperationResult(
                        account = account,
                        violations = listOfNotNull(it)
                )
            }

}