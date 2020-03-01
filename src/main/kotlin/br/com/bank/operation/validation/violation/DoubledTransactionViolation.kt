package br.com.bank.operation.validation.violation

class DoubledTransactionViolation(
        override val reason: String = "doubled-transaction",
        override val message: String = "Doubled transaction"
) : OperationViolation