package br.com.bank.operation.validation

import br.com.bank.operation.validation.violation.OperationViolation

object OperationValidation {
    fun hasViolation(violated: () -> Boolean, violation: OperationViolation): OperationViolation? =
            if (violated()) violation else null
}

