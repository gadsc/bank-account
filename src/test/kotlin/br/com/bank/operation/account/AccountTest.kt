package br.com.bank.operation.account

import br.com.bank.operation.OperationIdentifier
import br.com.bank.operation.account.transaction.Transaction
import br.com.bank.operation.validation.violation.DoubledTransactionViolation
import br.com.bank.operation.validation.violation.HighFrequencyViolation
import br.com.bank.operation.validation.violation.InsufficientLimitViolation
import org.junit.Assert.*
import org.junit.Test
import java.time.ZonedDateTime

class AccountTest {
    @Test
    fun `should create account from AccountOperationEvent`() {
        val accountOperationEvent = AccountOperationEvent(true, 100)
        val subject = Account.from(accountOperationEvent = accountOperationEvent)

        assertEquals(accountOperationEvent.activeCard, subject.activeCard)
        assertEquals(accountOperationEvent.availableLimit, subject.availableLimit)
        assertEquals(OperationIdentifier.ACCOUNT, subject.getIdentifier())
        assertTrue(subject.transactions.isEmpty())
    }

    @Test
    fun `should commit transaction when not found violations`() {
        val now = ZonedDateTime.now()
        val subject = Account(activeCard = true, availableLimit = 100)
        val transaction = Transaction("Burguer King", 20, time = now)

        val result = subject.commitTransaction(transaction = transaction)

        assertEquals(subject.activeCard, result.account!!.activeCard)
        assertEquals(subject.availableLimit - transaction.amount, result.account!!.availableLimit)
        assertEquals(1, result.account!!.transactions.size)
        assertEquals(transaction, result.account!!.transactions.first())
    }

    @Test
    fun `should not commit transaction when insufficient limit in account`() {
        val now = ZonedDateTime.now()
        val subject = Account(activeCard = true, availableLimit = 10)
        val transaction = Transaction("Burguer King", 20, time = now)

        val result = subject.commitTransaction(transaction = transaction)

        assertEquals(subject.activeCard, result.account!!.activeCard)
        assertEquals(subject.availableLimit, result.account!!.availableLimit)
        assertTrue(result.account!!.transactions.isEmpty())
        assertTrue(result.violations.isNotEmpty())
        assertEquals(InsufficientLimitViolation().reason, result.violations.first().reason)
    }

    @Test
    fun `should not commit transaction when doubled transaction in a small interval`() {
        val now = ZonedDateTime.now()
        val transaction = Transaction("Burguer King", 20, time = now)
        val subject = Account(activeCard = true, availableLimit = 100, transactions = listOf(transaction))


        val result = subject.commitTransaction(transaction = transaction)

        assertEquals(subject.activeCard, result.account!!.activeCard)
        assertEquals(subject.availableLimit, result.account!!.availableLimit)
        assertTrue(result.violations.isNotEmpty())
        assertEquals(DoubledTransactionViolation().reason, result.violations.first().reason)
    }

    @Test
    fun `should not commit transaction when high frequency transactions in a small interval`() {
        val now = ZonedDateTime.now()
        val transaction = Transaction("Burguer King", 20, time = now)
        val transaction2 = Transaction("Burguer King2", 20, time = now)
        val transaction3 = Transaction("Burguer King3", 20, time = now)
        val subject = Account(activeCard = true, availableLimit = 200, transactions = listOf(transaction, transaction2))

        val result = subject.commitTransaction(transaction = transaction3)

        assertEquals(subject.activeCard, result.account!!.activeCard)
        assertEquals(subject.availableLimit, result.account!!.availableLimit)
        assertTrue(result.violations.isNotEmpty())
        assertEquals(HighFrequencyViolation().reason, result.violations.first().reason)
    }

    @Test
    fun `should not commit transaction when insufficient limit and small interval violations`() {
        val now = ZonedDateTime.now()
        val transaction = Transaction("Burguer King", 20, time = now)
        val transaction2 = Transaction("Burguer King2", 20, time = now)
        val subject = Account(activeCard = true, availableLimit = 0, transactions = listOf(transaction, transaction2))

        val result = subject.commitTransaction(transaction = transaction2)

        println(result.violations)

        assertEquals(subject.activeCard, result.account!!.activeCard)
        assertEquals(subject.availableLimit, result.account!!.availableLimit)
        assertTrue(result.violations.isNotEmpty())
        assertTrue(result.violations.map { it.reason }
                .containsAll(listOf(HighFrequencyViolation().reason, DoubledTransactionViolation().reason, InsufficientLimitViolation().reason)))
    }
}