package violation

import br.com.bank.account.Operation

class DoubledTransactionViolation(
    override val reason: String = "doubled-transaction",
    override val message: String = "Doubled transaction"
) : OperationViolation {
//    override fun violationFor(operation: Operation?): OperationViolation? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
}