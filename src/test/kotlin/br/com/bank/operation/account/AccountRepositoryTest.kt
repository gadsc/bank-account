package br.com.bank.operation.account

import br.com.bank.operation.validation.violation.AccountAlreadyInitializedViolation
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AccountRepositoryTest {

    @Before
    fun init() {
        AccountRepository.clearActiveAccount()
    }

    @Test
    fun `should create account when process the operation`() {
        val account = Account(activeCard = true, availableLimit = 100)
        val operationResult = AccountRepository.createAccount(account)

        assertEquals(account.activeCard, operationResult.account?.activeCard)
        assertEquals(account.availableLimit, operationResult.account?.availableLimit)
        assertEquals(account.transactions, operationResult.account?.transactions)
        assertTrue(operationResult.violations.isEmpty())
    }

    @Test
    fun `should update active account`() {
        val account = Account(activeCard = true, availableLimit = 100)
        AccountRepository.createAccount(account)
        val updatedAccount = Account(activeCard = true, availableLimit = 50)
        val operationResult = AccountRepository.updateActiveAccount(updatedAccount)

        assertEquals(updatedAccount.activeCard, operationResult.account?.activeCard)
        assertEquals(updatedAccount.availableLimit, operationResult.account?.availableLimit)
        assertEquals(updatedAccount.transactions, operationResult.account?.transactions)
        assertTrue(operationResult.violations.isEmpty())
    }

    @Test
    fun `should not create account when process account already exists`() {
        val account = Account(activeCard = true, availableLimit = 100)
        AccountRepository.createAccount(account)
        val operationResult = AccountRepository.createAccount(account)

        assertTrue(operationResult.violations.isNotEmpty())
        assertEquals(AccountAlreadyInitializedViolation().reason, operationResult.violations.first().reason)
    }
}