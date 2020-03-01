package br.com.bank.operation.transaction

import br.com.bank.violation.DoubledTransactionViolation
import br.com.bank.violation.HighFrequencyViolation
import br.com.bank.violation.OperationValidation
import br.com.bank.violation.OperationViolation

object TransactionValidations {
    fun intervalValidations(transactions: List<Transaction>, transaction: Transaction): List<OperationViolation> =
            transactions
                    .filter { it.time.isBefore(transaction.time.minusMinutes(2)) || it.time.isEqual(transaction.time) }
                    .let {
                        listOfNotNull(doubleTransactionValidation(it, transaction),
                                highFrequencyViolation(it))
                    }

    private fun highFrequencyViolation(transactionInterval: List<Transaction>): OperationViolation? =
            OperationValidation.hasViolation({ transactionInterval.size >= 2 }, HighFrequencyViolation())

    private fun doubleTransactionValidation(transactionInterval: List<Transaction>, transaction: Transaction): OperationViolation? = OperationValidation
            .hasViolation({
                transactionInterval
                        .any { it.merchant == transaction.merchant && it.amount == transaction.amount }
            }, DoubledTransactionViolation())
}