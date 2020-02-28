package br.com.bank.account

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import java.time.ZonedDateTime

@JsonRootName("transaction")
data class TransactionOperationEvent(
    val merchant: String, val amount: Long,
    @JsonProperty("time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val time: ZonedDateTime
): OperationEvent {
    override fun toOperation(): Operation = Transaction.from(this)
}