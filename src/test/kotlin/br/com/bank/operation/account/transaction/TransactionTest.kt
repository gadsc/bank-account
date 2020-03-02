package br.com.bank.operation.account.transaction

import br.com.bank.operation.OperationIdentifier
import br.com.bank.operation.account.Account
import br.com.bank.operation.validation.violation.AccountNotInitializedViolation
import br.com.bank.operation.validation.violation.CardNotActiveViolation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.ZonedDateTime

class TransactionTest {
    @Test
    fun `should create transaction from TransactionOperationEvent`() {
        val transactionOperationEvent = TransactionOperationEvent(merchant = "Burguer King", amount = 100, time = ZonedDateTime.now())
        val subject = Transaction.from(transactionOperationEvent = transactionOperationEvent)

        assertEquals(transactionOperationEvent.merchant, subject.merchant)
        assertEquals(transactionOperationEvent.amount, subject.amount)
        assertEquals(transactionOperationEvent.time, subject.time)
        assertEquals(OperationIdentifier.TRANSACTION, subject.getIdentifier())
    }

    @Test
    fun `should commit transaction when account is ready`() {
        val now = ZonedDateTime.now()
        val account = Account(activeCard = true, availableLimit = 100)
        val subject = Transaction("Burguer King", 20, time = now)

        val result = subject.commit(account = account)

        assertEquals(account.activeCard, result.account!!.activeCard)
        assertEquals(account.availableLimit - subject.amount, result.account!!.availableLimit)
        assertEquals(1, result.account!!.transactions.size)
        assertEquals(subject, result.account!!.transactions.first())
    }

    @Test
    fun `should not commit transaction when account is not initialized`() {
        val now = ZonedDateTime.now()
        val subject = Transaction("Burguer King", 20, time = now)

        val result = subject.commit(account = null)

        assertTrue(result.violations.isNotEmpty())
        assertEquals(AccountNotInitializedViolation().reason, result.violations.first().reason)
    }

    @Test
    fun `should not commit transaction when account has not active card`() {
        val now = ZonedDateTime.now()
        val account = Account(activeCard = false, availableLimit = 100)
        val subject = Transaction("Burguer King", 20, time = now)

        val result = subject.commit(account = account)

        assertTrue(result.violations.isNotEmpty())
        assertEquals(CardNotActiveViolation().reason, result.violations.first().reason)
    }
}
