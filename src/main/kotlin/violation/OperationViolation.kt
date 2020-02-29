package violation

import br.com.bank.account.Operation

interface OperationViolation {
    val reason: String
    val message: String

    fun violationFor(operation: Operation?): OperationViolation?
//    fun findViolation(isViolated: () -> Boolean): OperationViolation? = if (isViolated()) this else null
}