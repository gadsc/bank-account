package violation

class AccountAlreadyInitializedViolation(
    override val reason: String = "account-already-initialized",
    override val message: String = "Account already exists"
) : OperationViolation