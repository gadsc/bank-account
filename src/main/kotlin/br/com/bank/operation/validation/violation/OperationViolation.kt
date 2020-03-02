package br.com.bank.operation.validation.violation

abstract class OperationViolation {
    abstract val reason: String
    abstract  val message: String
}
