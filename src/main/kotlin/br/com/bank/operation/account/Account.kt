package br.com.bank.operation.account

import br.com.bank.operation.Operation
import br.com.bank.operation.OperationIdentifier
import br.com.bank.operation.OperationResult
import br.com.bank.operation.account.AccountValidations.insufficientLimitValidation
import br.com.bank.operation.account.transaction.Transaction
import br.com.bank.operation.account.transaction.TransactionValidations.intervalValidations

data class Account(
        val activeCard: Boolean,
        val availableLimit: Long,
        val transactions: List<Transaction> = emptyList()
) : Operation {
    override fun getIdentifier(): OperationIdentifier = OperationIdentifier.ACCOUNT

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
            listOfNotNull(
                    insufficientLimitValidation(account = this, transaction = transaction)) +
                    intervalValidations(transactions = transactions, transaction = transaction)

    private fun executeTransaction(transaction: Transaction) = this.copy(
            availableLimit = this.availableLimit - transaction.amount,
            transactions = transactions + transaction
    ).apply { AccountRepository.updateActiveAccount(this) }
}
