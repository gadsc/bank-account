package br.com.bank.operation

import br.com.bank.operation.account.AccountOperationEvent
import br.com.bank.operation.transaction.TransactionOperationEvent
import com.fasterxml.jackson.databind.ObjectMapper

class OperationEventConverter(
        private val mapper: ObjectMapper
) {
    fun convertEvents(events: List<String>): List<OperationEvent> = events.map { convertEvent(it) }

    private fun convertEvent(event: String): OperationEvent = when {
        event.contains(OperationIdentifier.ACCOUNT.identifier) ->
            mapper.readValue(event, AccountOperationEvent::class.java) as OperationEvent
        else ->
            mapper.readValue(event, TransactionOperationEvent::class.java) as OperationEvent
    }
}
