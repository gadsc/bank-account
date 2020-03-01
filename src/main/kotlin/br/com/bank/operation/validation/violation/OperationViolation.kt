package br.com.bank.operation.validation.violation

interface OperationViolation {
    val reason: String
    val message: String
}
