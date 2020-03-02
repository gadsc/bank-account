package br.com.bank.operation.validation.violation

object AccountAlreadyInitializedViolation: OperationViolation() {
    override val reason: String = "account-already-initialized"
    override val message: String = "Account already exists"
}