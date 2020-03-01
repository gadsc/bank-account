package br.com.bank.operation.validation.violation

class InsufficientLimitViolation(
        override val reason: String = "insufficient-limit",
        override val message: String = "Insufficient Limit"
) : OperationViolation