package br.com.bank.operation.account.transaction

import br.com.bank.operation.account.AccountRepository
import br.com.bank.operation.objectMother.AccountObjectMother
import br.com.bank.operation.objectMother.TransactionObjectMother
import br.com.bank.operation.validation.violation.AccountNotInitializedViolation
import br.com.bank.operation.validation.violation.CardNotActiveViolation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TransactionOperationProcessorTest {
    private lateinit var subject: TransactionOperationProcessor

    @Before
    fun init() {
        AccountRepository.clearActiveAccount()
        subject = TransactionOperationProcessor()
    }

    @Test
    fun `should commit transaction when account is ready`() {
        val account = AccountObjectMother.build()
        AccountRepository.createAccount(account)
        val transaction = TransactionObjectMother.build()

        val result = subject.process(transaction)

        assertEquals(account.activeCard, result.account!!.activeCard)
        assertEquals(account.availableLimit - transaction.amount, result.account!!.availableLimit)
        assertEquals(1, result.account!!.transactions.size)
        assertEquals(transaction, result.account!!.transactions.first())
    }

    @Test
    fun `should not commit transaction when account is not initialized`() {
        val transaction = TransactionObjectMother.build()

        val result = subject.process(transaction)

        assertTrue(result.violations.isNotEmpty())
        assertEquals(AccountNotInitializedViolation, result.violations.first())
    }

    @Test
    fun `should not commit transaction when account has not active card`() {
        val account = AccountObjectMother.build(activeCard = false)
        AccountRepository.createAccount(account)
        val transaction = TransactionObjectMother.build()

        val result = subject.process(transaction)

        assertTrue(result.violations.isNotEmpty())
        assertEquals(CardNotActiveViolation, result.violations.first())
    }
}