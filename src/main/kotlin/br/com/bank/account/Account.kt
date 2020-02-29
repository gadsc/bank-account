package br.com.bank.account

import br.com.bank.account.AccountValidations.insufficientLimitValidation
import br.com.bank.account.TransactionValidations.intervalValidations

data class Account(
        val activeCard: Boolean,
        val availableLimit: Long,
        val transactions: List<Transaction> = emptyList()
) : Operation {
    companion object {
        fun from(accountOperationEvent: AccountOperationEvent) = Account(
                activeCard = accountOperationEvent.activeCard,
                availableLimit = accountOperationEvent.availableLimit
        )
    }

    fun commitTransaction(transaction: Transaction): OperationResult =
            getViolations(transaction).let {
                when {
                    it.isEmpty() -> OperationResult(account = executeTransaction(transaction), violations = emptyList())
                    else -> OperationResult(account = this, violations = it)
                }
            }

    private fun getViolations(transaction: Transaction) =
            listOfNotNull(insufficientLimitValidation(this, transaction)) + intervalValidations(transactions, transaction)

    private fun executeTransaction(transaction: Transaction) = this.copy(
            availableLimit = this.availableLimit - transaction.amount,
            transactions = transactions + transaction
    )
}