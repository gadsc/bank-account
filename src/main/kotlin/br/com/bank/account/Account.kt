package br.com.bank.account

data class Account(
    val activeCard: Boolean,
    val availableLimit: Long,
    val transactions: List<Transaction> = emptyList()
) {
    companion object {
        fun from(accountRequest: AccountRequest) = Account(
            activeCard = accountRequest.activeCard,
            availableLimit = accountRequest.availableLimit
        )
    }

    fun getViolations(transaction: Transaction): List<Violation> {
        val limitViolation: List<Violation> = if (availableLimit < transaction.amount) {
            listOf(
                Violation(
                    key = "insufficient-limit",
                    message = "Insufficient Limit"
                )
            )
        } else {
            emptyList()
        }

        val transactionInterval =
            transactions.filter { it.time.isBefore(transaction.time.minusMinutes(2)) || it.time.isEqual(transaction.time) }

        val intervalViolations: List<Violation> = if (transactionInterval.isNotEmpty()) {
            listOfNotNull(
                doubleTransactionValidation(transactionInterval, transaction),
                highFrequencyViolation(transactionInterval)
            )
        } else {
            emptyList()
        }

        return limitViolation + intervalViolations
    }

    private fun highFrequencyViolation(transactionInterval: List<Transaction>): Violation? =
        if (transactionInterval.size >= 2) {
            Violation(key = "high-frequency-small-interval", message = "High frequency small interval")
        } else {
            null
        }


    private fun doubleTransactionValidation(
        transactionInterval: List<Transaction>,
        transaction: Transaction
    ): Violation? =
        if (transactionInterval.any { it.merchant == transaction.merchant && it.amount == transaction.amount }) {
            Violation(key = "doubled-transaction", message = "Doubled transaction")
        } else {
            null
        }

    fun commitTransaction(transaction: Transaction): Pair<Account, List<Violation>> =
        if (availableLimit < transaction.amount) {
            Pair(this, listOf(Violation(key = "insufficient-limit", message = "Insufficient Limit")))
        } else {
            val violations = getViolations(transaction)

            if (violations.isEmpty()) {
                Pair(executeTransaction(transaction), emptyList())
            } else {
                Pair(this, violations)
            }
        }

    private fun executeTransaction(transaction: Transaction) = this.copy(
        availableLimit = this.availableLimit - transaction.amount,
        transactions = transactions + transaction
    )

}