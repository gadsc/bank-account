package br.com.bank.account

import java.time.ZonedDateTime

data class Transaction(val merchant: String, val amount: Long, val time: ZonedDateTime) : Operation {
    companion object {
        fun from(transactionOperationEvent: TransactionOperationEvent) = Transaction(
                merchant = transactionOperationEvent.merchant,
                amount = transactionOperationEvent.amount,
                time = transactionOperationEvent.time
        )
    }

    fun commit(account: Account?): OperationResult = Account.readyForTransaction(account).let { operationViolation ->
        operationViolation?.let { it -> OperationResult(account, it) }
                ?: account!!.commitTransaction(transaction = this@Transaction)
    }

}