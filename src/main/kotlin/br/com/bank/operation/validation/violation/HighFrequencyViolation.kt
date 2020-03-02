package br.com.bank.operation.validation.violation

object HighFrequencyViolation : OperationViolation() {
        override val reason: String = "high-frequency-small-interval"
        override val message: String = "High frequency small interval"
}
