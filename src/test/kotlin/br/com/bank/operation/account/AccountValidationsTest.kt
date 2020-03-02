package br.com.bank.operation.account

import br.com.bank.operation.objectMother.AccountObjectMother
import br.com.bank.operation.objectMother.TransactionObjectMother
import br.com.bank.operation.validation.violation.AccountAlreadyInitializedViolation
import br.com.bank.operation.validation.violation.AccountNotInitializedViolation
import br.com.bank.operation.validation.violation.CardNotActiveViolation
import br.com.bank.operation.validation.violation.InsufficientLimitViolation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AccountValidationsTest {
    @Test
    fun `should return null when account not initialized yet`() {
        val operationViolation = AccountValidations.accountAlreadyInitializedValidation(account = null)

        assertNull(operationViolation)
    }

    @Test
    fun `should return AccountAlreadyInitializedViolation when account already initialized`() {
        val account = AccountObjectMother.build()
        val operationViolation = AccountValidations.accountAlreadyInitializedValidation(account)

        assertEquals(AccountAlreadyInitializedViolation, operationViolation!!)
    }

    @Test
    fun `should return null when account has limit to execute the transaction`() {
        val account = AccountObjectMother.build()
        val transaction = TransactionObjectMother.build()
        val operationViolation = AccountValidations
                .insufficientLimitValidation(account = account, transaction = transaction)

        assertNull(operationViolation)
    }

    @Test
    fun `should return InsufficientLimitViolation when account has not limit to execute the transaction`() {
        val account = AccountObjectMother.build(availableLimit = 10)
        val transaction = TransactionObjectMother.build()
        val operationViolation = AccountValidations
                .insufficientLimitValidation(account = account, transaction = transaction)

        assertEquals(InsufficientLimitViolation, operationViolation!!)
    }

    @Test
    fun `should return null when account is ready for transactions`() {
        val account = AccountObjectMother.build()
        val operationViolation = AccountValidations.readyForTransaction(account)

        assertNull(operationViolation)
    }

    @Test
    fun `should return AccountNotInitializedViolation when account is null`() {
        val operationViolation = AccountValidations.readyForTransaction(account = null)

        assertEquals(AccountNotInitializedViolation, operationViolation!!)
    }

    @Test
    fun `should return CardNotActiveViolation when the card for account is not active`() {
        val account = AccountObjectMother.build(activeCard = false)
        val operationViolation = AccountValidations.readyForTransaction(account)

        assertEquals(CardNotActiveViolation, operationViolation!!)
    }
}
