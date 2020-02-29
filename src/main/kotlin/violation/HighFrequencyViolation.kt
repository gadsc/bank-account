package violation

import br.com.bank.account.Operation

class HighFrequencyViolation(
    override val reason: String = "high-frequency-small-interval",
    override val message: String = "High frequency small interval"
) : OperationViolation {
    override fun violationFor(operation: Operation?): OperationViolation? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}