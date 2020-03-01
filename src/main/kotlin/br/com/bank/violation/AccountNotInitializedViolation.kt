package br.com.bank.violation

class AccountNotInitializedViolation(
        override val reason: String = "account-not-initialized",
        override val message: String = "Account not initialized"
) : OperationViolation