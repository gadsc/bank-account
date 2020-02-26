package br.com.bank.account

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("account")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class AccountResponse(
    @JsonProperty("active-card")
    val activeCard: Boolean?,
    @JsonProperty("available-limit")
    val availableLimit: Long?,
    @JsonProperty("violations")
    val violations: List<String>
) {
    companion object {
        fun from(accountWithViolations: Pair<Account?, List<Violation>>): AccountResponse = AccountResponse(
            activeCard = accountWithViolations?.first?.activeCard,
            availableLimit = accountWithViolations?.first?.availableLimit,
            violations = accountWithViolations.second.map { it.key }
        )
    }
}