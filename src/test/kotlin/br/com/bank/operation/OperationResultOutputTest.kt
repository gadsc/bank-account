package br.com.bank.operation

import br.com.bank.operation.objectMother.AccountObjectMother
import br.com.bank.operation.validation.violation.AccountAlreadyInitializedViolation
import org.junit.Assert.*
import org.junit.Test

class OperationResultOutputTest {
    @Test
    fun `should not return account when account result output is null`() {
        val operationResult = OperationResult(account = null, violations = emptyList())
        val subject = OperationResultOutput.from(operationResult)

        assertNull(subject.account)
        assertTrue(subject.violations.isEmpty())
    }

    @Test
    fun `should return account in the operation result output`() {
        val account = AccountObjectMother.build()
        val operationResult = OperationResult(account = account, violations = emptyList())
        val subject = OperationResultOutput.from(operationResult)

        assertEquals(account.activeCard, subject.account?.activeCard)
        assertEquals(account.availableLimit, subject.account?.availableLimit)
        assertTrue(subject.violations.isEmpty())
    }

    @Test
    fun `should return account in the operation result output with violations`() {
        val account = AccountObjectMother.build()
        val violations = listOf(AccountAlreadyInitializedViolation)
        val operationResult = OperationResult(account = account, violations = violations)
        val subject = OperationResultOutput.from(operationResult)

        assertEquals(account.activeCard, subject.account?.activeCard)
        assertEquals(account.availableLimit, subject.account?.availableLimit)
        assertTrue(subject.violations.isNotEmpty())
        assertEquals(violations.map { it.reason }, subject.violations)
    }
}