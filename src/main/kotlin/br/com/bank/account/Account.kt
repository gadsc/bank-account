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

    fun commitTransaction(transaction: Transaction): Account =
        if (availableLimit < transaction.amount) {
            throw RuntimeException("Insufficient Limit")
        } else {
            val transactionInterval = transactions.filter { it.time.isBefore(transaction.time.minusMinutes(2)) || it.time.isEqual(transaction.time) }
            if (transactionInterval.isNotEmpty()) {
                if (transactionInterval.any { it.merchant == transaction.merchant && it.amount == transaction.amount }) throw RuntimeException(
                    "Doubled transaction"
                )
                else if (transactionInterval.size >= 3) {
                    throw RuntimeException("High frequency small interval")
                } else {
                    Account(
                        activeCard = this.activeCard,
                        availableLimit = this.availableLimit - transaction.amount,
                        transactions = transactions + transaction
                    )
                }
            } else {
                Account(
                    activeCard = this.activeCard,
                    availableLimit = this.availableLimit - transaction.amount,
                    transactions = transactions + transaction
                )
            }
        }
}