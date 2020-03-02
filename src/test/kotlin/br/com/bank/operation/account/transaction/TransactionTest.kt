package br.com.bank.operation.account.transaction

import br.com.bank.operation.OperationIdentifier
import br.com.bank.operation.objectMother.AccountObjectMother
import br.com.bank.operation.objectMother.TransactionObjectMother
import br.com.bank.operation.objectMother.TransactionOperationEventObjectMother
import br.com.bank.operation.validation.violation.AccountNotInitializedViolation
import br.com.bank.operation.validation.violation.CardNotActiveViolation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TransactionTest {
    @Test
    fun `should create transaction from TransactionOperationEvent`() {
        val transactionOperationEvent = TransactionOperationEventObjectMother.build()
        val subject = Transaction.from(transactionOperationEvent = transactionOperationEvent)

        assertEquals(transactionOperationEvent.merchant, subject.merchant)
        assertEquals(transactionOperationEvent.amount, subject.amount)
        assertEquals(transactionOperationEvent.time, subject.time)
        assertEquals(OperationIdentifier.TRANSACTION, subject.getIdentifier())
    }

    @Test
    fun `should commit transaction when account is ready`() {
        val account = AccountObjectMother.build()
        val subject = TransactionObjectMother.build()

        val result = subject.commit(account = account)

        assertEquals(account.activeCard, result.account!!.activeCard)
        assertEquals(account.availableLimit - subject.amount, result.account!!.availableLimit)
        assertEquals(1, result.account!!.transactions.size)
        assertEquals(subject, result.account!!.transactions.first())
    }

    @Test
    fun `should not commit transaction when account is not initialized`() {
        val subject = TransactionObjectMother.build()

        val result = subject.commit(account = null)

        assertTrue(result.violations.isNotEmpty())
        assertEquals(AccountNotInitializedViolation, result.violations.first())
    }

    @Test
    fun `should not commit transaction when account has not active card`() {
        val account = AccountObjectMother.build(activeCard = false)
        val subject = TransactionObjectMother.build()

        val result = subject.commit(account = account)

        assertTrue(result.violations.isNotEmpty())
        assertEquals(CardNotActiveViolation, result.violations.first())
    }
}
