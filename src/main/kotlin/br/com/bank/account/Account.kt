package br.com.bank.account

import violation.*

data class Account(
        val activeCard: Boolean,
        val availableLimit: Long,
        val transactions: List<Transaction> = emptyList()
) : Operation {
//    override fun validations(): List<OperationValidation> = listOf(AccountAlreadyInitializedViolation())

    companion object {
        fun from(accountOperationEvent: AccountOperationEvent) = Account(
                activeCard = accountOperationEvent.activeCard,
                availableLimit = accountOperationEvent.availableLimit
        )

        fun accountAlreadyInitializedValidation(account: Account?) =
                OperationValidation.findViolation({ account != null }, AccountAlreadyInitializedViolation())

        private fun accountNotInitializedValidation(account: Account?) =
                OperationValidation.findViolation({ account == null }, AccountNotInitializedViolation())

        private fun cardNotActiveValidation(account: Account?) =
                OperationValidation.findViolation({ account != null && !account.activeCard }, CardNotActiveViolation())

        fun readyForTransaction(account: Account?) =
                accountNotInitializedValidation(account) ?: cardNotActiveValidation(account)

        fun insufficientLimitViolation(account: Account, transaction: Transaction): OperationViolation? =
                OperationValidation.findViolation({ account.availableLimit < transaction.amount }, InsufficientLimitViolation())

        fun intervalValidations(transactions: List<Transaction>, transaction: Transaction): List<OperationViolation> =
                transactions
                        .filter { it.time.isBefore(transaction.time.minusMinutes(2)) || it.time.isEqual(transaction.time) }
                        .let {
                            listOfNotNull(doubleTransactionValidation(it, transaction),
                                    highFrequencyViolation(it))
                        }

        private fun highFrequencyViolation(transactionInterval: List<Transaction>): OperationViolation? =
                OperationValidation.findViolation({ transactionInterval.size >= 2 }, HighFrequencyViolation())

        private fun doubleTransactionValidation(transactionInterval: List<Transaction>, transaction: Transaction): OperationViolation? = OperationValidation
                .findViolation({
                    transactionInterval
                            .any { it.merchant == transaction.merchant && it.amount == transaction.amount }
                }, DoubledTransactionViolation())
    }

    private fun getViolations(transaction: Transaction) =
            listOfNotNull(insufficientLimitViolation(this, transaction)) + intervalValidations(transactions, transaction)

    fun commitTransaction(transaction: Transaction): OperationResult =
            getViolations(transaction).let {
                when {
                    it.isEmpty() -> OperationResult(account = executeTransaction(transaction), violations = emptyList())
                    else -> OperationResult(account = this, violations = it)
                }
            }

    private fun executeTransaction(transaction: Transaction) = this.copy(
            availableLimit = this.availableLimit - transaction.amount,
            transactions = transactions + transaction
    )

}