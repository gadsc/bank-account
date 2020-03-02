package br.com.bank.operation.account

import br.com.bank.operation.validation.violation.AccountAlreadyInitializedViolation
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AccountOperationProcessorTest {
    private lateinit var subject: AccountOperationProcessor

    @Before
    fun init() {
        subject = AccountOperationProcessor()
    }

    @Test
    fun `should create account when process the operation`() {
        val account = Account(activeCard = true, availableLimit = 100)
        val operationResult = subject.process(account)

        assertEquals(account.activeCard, operationResult.account?.activeCard)
        assertEquals(account.availableLimit, operationResult.account?.availableLimit)
        assertEquals(account.transactions, operationResult.account?.transactions)
        assertTrue(operationResult.violations.isEmpty())
    }

    @Test
    fun `should not create account when process account already exists`() {
        val account = Account(activeCard = true, availableLimit = 100)
        AccountRepository.createAccount(account)

        val operationResult = subject.process(account)


        assertTrue(operationResult.violations.isNotEmpty())
        assertEquals(AccountAlreadyInitializedViolation().reason, operationResult.violations.first().reason)
    }
}