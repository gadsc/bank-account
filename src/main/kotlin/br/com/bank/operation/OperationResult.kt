package br.com.bank.operation

import br.com.bank.operation.account.Account
import br.com.bank.operation.validation.violation.OperationViolation

data class OperationResult(val account: Account?, val violations: List<OperationViolation>) {
    constructor(account: Account?, violation: OperationViolation?) : this(account = account, violations = listOfNotNull(violation))
}
