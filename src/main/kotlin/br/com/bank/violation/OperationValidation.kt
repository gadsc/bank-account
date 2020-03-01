package br.com.bank.violation

object OperationValidation {
    fun hasViolation(violated: () -> Boolean, violation: OperationViolation): OperationViolation? =
            if (violated()) violation else null
}

