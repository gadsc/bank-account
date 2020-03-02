package br.com.bank.operation.account

import br.com.bank.operation.Operation
import br.com.bank.operation.OperationEvent
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
