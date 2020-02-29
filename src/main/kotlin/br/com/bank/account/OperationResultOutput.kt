package br.com.bank.account

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

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AccountResultOutput(val activeCard: Boolean?, val availableLimit: Long?) {
    companion object {
        fun from(account: Account): AccountResultOutput = AccountResultOutput(
                activeCard = account.activeCard,
                availableLimit = account.availableLimit
        )
    }
}