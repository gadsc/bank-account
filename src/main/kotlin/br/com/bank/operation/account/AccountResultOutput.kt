package br.com.bank.operation.account

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AccountResultOutput(val activeCard: Boolean?, val availableLimit: Long?) {
    companion object {
        fun from(account: Account): AccountResultOutput = AccountResultOutput(
                activeCard = account.activeCard,
                availableLimit = account.availableLimit
        )
    }
}