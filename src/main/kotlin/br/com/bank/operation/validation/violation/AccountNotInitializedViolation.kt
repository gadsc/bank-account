package br.com.bank.operation.validation.violation

object AccountNotInitializedViolation : OperationViolation(){
        override val reason: String = "account-not-initialized"
        override val message: String = "Account not initialized"
}