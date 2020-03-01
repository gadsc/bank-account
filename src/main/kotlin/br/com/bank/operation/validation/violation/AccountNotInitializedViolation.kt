package br.com.bank.operation.validation.violation

class AccountNotInitializedViolation(
        override val reason: String = "account-not-initialized",
        override val message: String = "Account not initialized"
) : OperationViolation