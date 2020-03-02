package br.com.bank.operation.account

import br.com.bank.operation.OperationIdentifier
import br.com.bank.operation.account.transaction.Transaction
import br.com.bank.operation.objectMother.AccountObjectMother
import br.com.bank.operation.objectMother.AccountOperationEventObjectMother
import br.com.bank.operation.objectMother.TransactionObjectMother
import br.com.bank.operation.validation.violation.DoubledTransactionViolation
import br.com.bank.operation.validation.violation.HighFrequencyViolation
import br.com.bank.operation.validation.violation.InsufficientLimitViolation
import org.junit.Assert.*
import org.junit.Test
import java.time.ZonedDateTime

class AccountTest {
    @Test
    fun `should create account from AccountOperationEvent`() {
        val accountOperationEvent = AccountOperationEventObjectMother.build()
        val subject = Account.from(accountOperationEvent = accountOperationEvent)

        assertEquals(accountOperationEvent.activeCard, subject.activeCard)
        assertEquals(accountOperationEvent.availableLimit, subject.availableLimit)
        assertEquals(OperationIdentifier.ACCOUNT, subject.getIdentifier())
        assertTrue(subject.transactions.isEmpty())
    }

    @Test
    fun `should commit transaction when not found violations`() {
        val subject = AccountObjectMother.build()
        val transaction = TransactionObjectMother.build()

        val result = subject.commitTransaction(transaction = transaction)

        assertEquals(subject.activeCard, result.account!!.activeCard)
        assertEquals(subject.availableLimit - transaction.amount, result.account!!.availableLimit)
        assertEquals(1, result.account!!.transactions.size)
        assertEquals(transaction, result.account!!.transactions.first())
    }

    @Test
    fun `should not commit transaction when insufficient limit in account`() {
        val subject = AccountObjectMother.build(availableLimit = 10)
        val transaction = TransactionObjectMother.build()

        val result = subject.commitTransaction(transaction = transaction)

        assertEquals(subject.activeCard, result.account!!.activeCard)
        assertEquals(subject.availableLimit, result.account!!.availableLimit)
        assertTrue(result.account!!.transactions.isEmpty())
        assertTrue(result.violations.isNotEmpty())
        assertEquals(InsufficientLimitViolation, result.violations.first())
    }

    @Test
    fun `should not commit transaction when doubled transaction in a small interval`() {
        val transaction = TransactionObjectMother.build()
        val subject = AccountObjectMother.build(transactions = listOf(transaction))

        val result = subject.commitTransaction(transaction = transaction)

        assertEquals(subject.activeCard, result.account!!.activeCard)
        assertEquals(subject.availableLimit, result.account!!.availableLimit)
        assertTrue(result.violations.isNotEmpty())
        assertEquals(DoubledTransactionViolation, result.violations.first())
    }

    @Test
    fun `should not commit transaction when high frequency transactions in a small interval`() {
        val subject = AccountObjectMother.build(transactions = TransactionObjectMother.buildMany(amountOfTransactions = 2))
        val result = subject.commitTransaction(transaction = TransactionObjectMother.build())

        assertEquals(subject.activeCard, result.account!!.activeCard)
        assertEquals(subject.availableLimit, result.account!!.availableLimit)
        assertTrue(result.violations.isNotEmpty())
        assertEquals(HighFrequencyViolation, result.violations.first())
    }

    @Test
    fun `should not commit transaction when insufficient limit and small interval violations`() {
        val transaction = TransactionObjectMother.build(merchant = "New merchant")
        val subject = AccountObjectMother.build(transactions = listOf(TransactionObjectMother.build(), transaction))

        val result = subject.commitTransaction(transaction = transaction)

        assertEquals(subject.activeCard, result.account!!.activeCard)
        assertEquals(subject.availableLimit, result.account!!.availableLimit)
        assertTrue(result.violations.isNotEmpty())
        assertTrue(result.violations
                .containsAll(listOf(HighFrequencyViolation, DoubledTransactionViolation, InsufficientLimitViolation)))
    }
}