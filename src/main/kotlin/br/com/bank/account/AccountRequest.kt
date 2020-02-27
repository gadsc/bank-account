package br.com.bank.account

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("account")
data class AccountRequest(
    @JsonProperty("active-card")
    val activeCard: Boolean,
    @JsonProperty("available-limit")
    val availableLimit: Long
): BankEvent