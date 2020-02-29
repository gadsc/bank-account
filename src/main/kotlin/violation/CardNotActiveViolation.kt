package violation

import br.com.bank.account.Account
import br.com.bank.account.Operation

class CardNotActiveViolation(
        override val reason: String = "card-not-active",
        override val message: String = "Card not active"
) : OperationViolation {
//    override fun violationFor(operation: Operation?): OperationViolation? =
//            OperationValidation.findViolation({ operation != null && !(operation as Account).activeCard }, this)
}