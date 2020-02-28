package br.com.bank.account

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("account")
data class AccountOperationEvent(
    @JsonProperty("active-card")
    val activeCard: Boolean,
    @JsonProperty("available-limit")
    val availableLimit: Long
): OperationEvent {
    override fun toOperation(): Operation = Account.from(this)
}