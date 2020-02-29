package violation

import br.com.bank.account.Account
import br.com.bank.account.Operation

//class AccountCreateValidation: OperationValidation {
//    override val violations: List<OperationViolation> = listOf(AccountAlreadyInitializedViolation())
//}

object OperationValidations {
    val CREATE_ACCOUNT = listOf(AccountAlreadyInitializedViolation())
    val TRANSACTION_INPUT = listOf(AccountNotInitializedViolation(), CardNotActiveViolation())
}