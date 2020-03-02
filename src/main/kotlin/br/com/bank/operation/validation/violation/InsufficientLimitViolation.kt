package br.com.bank.operation.validation.violation

object InsufficientLimitViolation : OperationViolation() {
    override val reason: String = "insufficient-limit"
    override val message: String = "Insufficient Limit"
}