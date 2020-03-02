package br.com.bank.operation.validation

import br.com.bank.operation.validation.violation.InsufficientLimitViolation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class OperationValidationTest {
    @Test
    fun `should return null when not true the function that valid the violation`() {
        val availableLimit = 1
        val transactionAmount = 1

        assertNull(OperationValidation.hasViolation({ availableLimit < transactionAmount }, InsufficientLimitViolation))
    }

    @Test
    fun `should return the violations when true the function that valid the violation`() {
        val availableLimit = 1
        val transactionAmount = 2

        assertEquals(InsufficientLimitViolation, OperationValidation
                .hasViolation({ availableLimit < transactionAmount }, InsufficientLimitViolation)
        )
    }
}