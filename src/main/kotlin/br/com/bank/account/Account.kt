package br.com.bank.account

import violation.*

data class Account(
    val activeCard: Boolean,
    val availableLimit: Long,
    val transactions: List<Transaction> = emptyList()
): Operation {
//    override fun validations(): List<OperationValidation> = listOf(AccountAlreadyInitializedViolation())

    companion object {
        fun from(accountOperationEvent: AccountOperationEvent) = Account(
            activeCard = accountOperationEvent.activeCard,
            availableLimit = accountOperationEvent.availableLimit
        )
    }

    fun getViolations(transaction: Transaction): List<OperationViolation> {
        val limitViolation: List<OperationViolation> = if (availableLimit < transaction.amount) {
            listOf(
                InsufficientLimitViolation()
            )
        } else {
            emptyList()
        }

        val transactionInterval =
            transactions.filter { it.time.isBefore(transaction.time.minusMinutes(2)) || it.time.isEqual(transaction.time) }

        val intervalViolations: List<OperationViolation> = if (transactionInterval.isNotEmpty()) {
            listOfNotNull(
                doubleTransactionValidation(transactionInterval, transaction),
                highFrequencyViolation(transactionInterval)
            )
        } else {
            emptyList()
        }

        return limitViolation + intervalViolations
    }

    private fun highFrequencyViolation(transactionInterval: List<Transaction>): OperationViolation? =
        if (transactionInterval.size >= 2) {
            HighFrequencyViolation()
        } else {
            null
        }


    private fun doubleTransactionValidation(
        transactionInterval: List<Transaction>,
        transaction: Transaction
    ): OperationViolation? =
        if (transactionInterval.any { it.merchant == transaction.merchant && it.amount == transaction.amount }) {
            DoubledTransactionViolation()
        } else {
            null
        }

    fun commitTransaction(transaction: Transaction): OperationResult {
//        if (availableLimit < transaction.amount) {
//            Pair(this, listOf(Violation(key = "insufficient-limit", message = "Insufficient Limit")))
//        } else {
            val violations = getViolations(transaction)

            return if (violations.isEmpty()) {
                OperationResult(account = executeTransaction(transaction), violations = emptyList())
            } else {
                OperationResult(account = this, violations = violations)
            }
        }

    private fun executeTransaction(transaction: Transaction) = this.copy(
        availableLimit = this.availableLimit - transaction.amount,
        transactions = transactions + transaction
    )

}