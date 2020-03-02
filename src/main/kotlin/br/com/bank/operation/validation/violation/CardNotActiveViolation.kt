package br.com.bank.operation.validation.violation

object CardNotActiveViolation  : OperationViolation() {
        override val reason: String = "card-not-active"
        override val message: String = "Card not active"
}