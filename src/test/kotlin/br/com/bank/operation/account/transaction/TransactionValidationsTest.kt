package br.com.bank.operation.account.transaction

import br.com.bank.operation.validation.violation.DoubledTransactionViolation
import br.com.bank.operation.validation.violation.HighFrequencyViolation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.ZonedDateTime

class TransactionValidationsTest {
    @Test
    fun `should return empty list of violations when none violation in a small interval was found`() {
        val now = ZonedDateTime.now()
        val transaction = Transaction("Burguer King", 20, time = now)
        val result = TransactionValidations.intervalValidations(emptyList(), transaction)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should return DoubledTransactionViolation when doubled transaction in a small interval`() {
        val now = ZonedDateTime.now()
        val transaction = Transaction("Burguer King", 20, time = now)
        val result = TransactionValidations.intervalValidations(listOf(transaction), transaction)

        assertEquals(1, result.size)
        assertEquals(DoubledTransactionViolation().reason, result.first().reason)
    }

    @Test
    fun `should return HighFrequencyViolation when high frequency transactions in a small interval`() {
        val now = ZonedDateTime.now()
        val transaction = Transaction("Burguer King", 20, time = now)
        val transaction2 = Transaction("Burguer King2", 20, time = now)
        val transaction3 = Transaction("Burguer King3", 20, time = now)

        val result = TransactionValidations.intervalValidations( listOf(transaction, transaction2), transaction3)

        assertEquals(1, result.size)
        assertEquals(HighFrequencyViolation().reason, result.first().reason)
    }

    @Test
    fun `should return small interval violations`() {
        val now = ZonedDateTime.now()
        val transaction = Transaction("Burguer King", 20, time = now)
        val transaction2 = Transaction("Burguer King2", 20, time = now)

        val result = TransactionValidations.intervalValidations( listOf(transaction, transaction2), transaction2)

        assertEquals(2, result.size)
        assertTrue(result.map { it.reason }.containsAll(listOf(HighFrequencyViolation().reason, DoubledTransactionViolation().reason)))
    }
}