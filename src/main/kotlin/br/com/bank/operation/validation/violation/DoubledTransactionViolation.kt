package br.com.bank.operation.validation.violation

object DoubledTransactionViolation : OperationViolation() {
    override val reason: String = "doubled-transaction"
    override val message: String = "Doubled transaction"
}
