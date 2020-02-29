package violation

import br.com.bank.account.Operation

class AccountNotInitializedViolation(
        override val reason: String = "account-not-initialized",
        override val message: String = "Account not initialized"
) : OperationViolation {
//    override fun violationFor(operation: Operation?): OperationViolation? =
//            OperationValidation.findViolation({ operation == null }, this)
//    override fun isValid(operation: Operation): OperationViolation? = if (operation != null) null else this
}