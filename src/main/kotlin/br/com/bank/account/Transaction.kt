package br.com.bank.account

import br.com.bank.account.AccountValidations.readyForTransaction
import java.time.ZonedDateTime

data class Transaction(val merchant: String, val amount: Long, val time: ZonedDateTime) : Operation {
    override fun getIdentifier(): OperationIdentifier = OperationIdentifier.TRANSACTION

    companion object {
        fun from(transactionOperationEvent: TransactionOperationEvent) = Transaction(
                merchant = transactionOperationEvent.merchant,
                amount = transactionOperationEvent.amount,
                time = transactionOperationEvent.time
        )
    }

    fun commit(account: Account?): OperationResult = readyForTransaction(account).let { operationViolation ->
        operationViolation?.let { it -> OperationResult(account, it) }
                ?: account!!.commitTransaction(transaction = this@Transaction)
    }

}