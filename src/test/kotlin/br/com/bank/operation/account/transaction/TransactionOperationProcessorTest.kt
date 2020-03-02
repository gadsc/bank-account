package br.com.bank.operation.account.transaction

import br.com.bank.operation.account.Account
import br.com.bank.operation.account.AccountRepository
import br.com.bank.operation.validation.violation.AccountNotInitializedViolation
import br.com.bank.operation.validation.violation.CardNotActiveViolation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime

class TransactionOperationProcessorTest {
    private lateinit var subject: TransactionOperationProcessor

    @Before
    fun init() {
        AccountRepository.clearActiveAccount()
        subject = TransactionOperationProcessor()
    }

    @Test
    fun `should commit transaction when account is ready`() {
        val now = ZonedDateTime.now()
        val account = Account(activeCard = true, availableLimit = 100)
        AccountRepository.createAccount(account)
        val transaction = Transaction("Burguer King", 20, time = now)

        val result = subject.process(transaction)

        assertEquals(account.activeCard, result.account!!.activeCard)
        assertEquals(account.availableLimit - transaction.amount, result.account!!.availableLimit)
        assertEquals(1, result.account!!.transactions.size)
        assertEquals(transaction, result.account!!.transactions.first())
    }

    @Test
    fun `should not commit transaction when account is not initialized`() {
        val now = ZonedDateTime.now()
        val transaction = Transaction("Burguer King", 20, time = now)

        val result = subject.process(transaction)

        assertTrue(result.violations.isNotEmpty())
        assertEquals(AccountNotInitializedViolation().reason, result.violations.first().reason)
    }

    @Test
    fun `should not commit transaction when account has not active card`() {
        val now = ZonedDateTime.now()
        val account = Account(activeCard = false, availableLimit = 100)
        AccountRepository.createAccount(account)
        val transaction = Transaction("Burguer King", 20, time = now)

        val result = subject.process(transaction)

        assertTrue(result.violations.isNotEmpty())
        assertEquals(CardNotActiveViolation().reason, result.violations.first().reason)
    }
}