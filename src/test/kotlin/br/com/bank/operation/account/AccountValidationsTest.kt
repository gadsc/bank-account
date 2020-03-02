package br.com.bank.operation.account

import br.com.bank.operation.account.transaction.Transaction
import br.com.bank.operation.validation.violation.AccountAlreadyInitializedViolation
import br.com.bank.operation.validation.violation.AccountNotInitializedViolation
import br.com.bank.operation.validation.violation.CardNotActiveViolation
import br.com.bank.operation.validation.violation.InsufficientLimitViolation
import org.junit.Assert.*
import org.junit.Test
import java.time.ZonedDateTime

class AccountValidationsTest {
    @Test
    fun `should return null when account not initialized yet`() {
        val operationViolation = AccountValidations.accountAlreadyInitializedValidation(null)

        assertNull(operationViolation)
    }

    @Test
    fun `should return AccountAlreadyInitializedViolation when account already initialized`() {
        val account = Account(activeCard = true, availableLimit = 100)
        val operationViolation = AccountValidations.accountAlreadyInitializedValidation(account)

        assertEquals(AccountAlreadyInitializedViolation, operationViolation!!)
    }

    @Test
    fun `should return null when account has limit to execute the transaction`() {
        val account = Account(activeCard = true, availableLimit = 100)
        val transaction = Transaction(merchant = "Burguer King", amount = 20, time = ZonedDateTime.now())
        val operationViolation = AccountValidations
                .insufficientLimitValidation(account = account, transaction = transaction)

        assertNull(operationViolation)
    }

    @Test
    fun `should return InsufficientLimitViolation when account has not limit to execute the transaction`() {
        val account = Account(activeCard = true, availableLimit = 10)
        val transaction = Transaction(merchant = "Burguer King", amount = 20, time = ZonedDateTime.now())
        val operationViolation = AccountValidations
                .insufficientLimitValidation(account = account, transaction = transaction)

        assertEquals(InsufficientLimitViolation, operationViolation!!)
    }

    @Test
    fun `should return null when account is ready for transactions`() {
        val account = Account(activeCard = true, availableLimit = 100)
        val operationViolation = AccountValidations.readyForTransaction(account)

        assertNull(operationViolation)
    }

    @Test
    fun `should return AccountNotInitializedViolation when account is null`() {
        val operationViolation = AccountValidations.readyForTransaction(null)

        assertEquals(AccountNotInitializedViolation, operationViolation!!)
    }

    @Test
    fun `should return CardNotActiveViolation when the card for account is not active`() {
        val account = Account(activeCard = false, availableLimit = 100)
        val operationViolation = AccountValidations.readyForTransaction(account)

        assertEquals(CardNotActiveViolation, operationViolation!!)
    }
}
