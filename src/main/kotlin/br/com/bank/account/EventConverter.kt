package br.com.bank.account

import com.fasterxml.jackson.databind.ObjectMapper

class EventConverter(
    private val mapper: ObjectMapper
) {
    fun convertEvents(events: List<String>): List<BankEvent> = events.map { convertEvent(it) }

    private fun convertEvent(event: String): BankEvent = if (event.contains(OperationIdentifier.ACCOUNT.identifier)) {
        mapper.readValue(event, AccountRequest::class.java) as BankEvent
    } else {
        mapper.readValue(event, TransactionRequest::class.java) as BankEvent
    }
}
