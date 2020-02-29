package violation

import br.com.bank.account.Operation

class InsufficientLimitViolation(
    override val reason: String = "insufficient-limit",
    override val message: String = "Insufficient Limit"
) : OperationViolation {
    override fun violationFor(operation: Operation?): OperationViolation? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}