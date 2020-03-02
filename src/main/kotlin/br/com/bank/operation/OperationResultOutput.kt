package br.com.bank.operation

import br.com.bank.operation.account.AccountResultOutput
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OperationResultOutput(val account: AccountResultOutput?, val violations: List<String>) {
    companion object {
        fun from(operationResult: OperationResult): OperationResultOutput = OperationResultOutput(
                account = when {
                    operationResult.account != null -> AccountResultOutput.from(account = operationResult.account)
                    else -> null
                },
                violations = operationResult.violations.map { it.reason }
        )
    }
}
