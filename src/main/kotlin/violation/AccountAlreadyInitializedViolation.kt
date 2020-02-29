package violation

import br.com.bank.account.Operation

class AccountAlreadyInitializedViolation(
    override val reason: String = "account-already-initialized",
    override val message: String = "Account already exists"
) : OperationViolation {
    override fun violationFor(operation: Operation?): OperationViolation? =
            OperationValidation.findViolation({ operation != null }, this)

//    override fun findViolation(isViolated: () -> Boolean): OperationViolation? {
//        return super.findViolation(isViolated)
//    }
}