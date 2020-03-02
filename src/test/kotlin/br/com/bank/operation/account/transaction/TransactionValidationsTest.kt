package br.com.bank.operation.account.transaction

import br.com.bank.operation.objectMother.TransactionObjectMother
import br.com.bank.operation.validation.violation.DoubledTransactionViolation
import br.com.bank.operation.validation.violation.HighFrequencyViolation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TransactionValidationsTest {
    @Test
    fun `should return empty list of violations when none violation in a small interval was found`() {
        val transaction = TransactionObjectMother.build()
        val result = TransactionValidations.intervalValidations(emptyList(), transaction)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return DoubledTransactionViolation when doubled transaction in a small interval`() {
        val transaction = TransactionObjectMother.build()
        val result = TransactionValidations.intervalValidations(listOf(transaction), transaction)

        assertEquals(1, result.size)
        assertEquals(DoubledTransactionViolation, result.first())
    }

    @Test
    fun `should return HighFrequencyViolation when high frequency transactions in a small interval`() {
        val transaction = TransactionObjectMother.build()
        val transactions = TransactionObjectMother.buildMany(amountOfTransactions = 2)

        val result = TransactionValidations.intervalValidations( transactions, transaction)

        assertEquals(1, result.size)
        assertEquals(HighFrequencyViolation, result.first())
    }

    @Test
    fun `should return small interval violations`() {
        val transaction = TransactionObjectMother.build()
        val transaction2 = TransactionObjectMother.build("Burguer King2")

        val result = TransactionValidations.intervalValidations( listOf(transaction, transaction2), transaction2)

        assertEquals(2, result.size)
        assertTrue(result.containsAll(listOf(HighFrequencyViolation, DoubledTransactionViolation)))
    }
}