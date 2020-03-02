package br.com.bank.operation

import br.com.bank.operation.account.AccountOperationEvent
import br.com.bank.operation.account.transaction.TransactionOperationEvent
import com.fasterxml.jackson.databind.ObjectMapper

class OperationEventConverter(
        private val mapper: ObjectMapper
) {
    companion object {
        private const val JSON_ROOT_POSITION = 2
    }

    fun convertEvents(events: List<String>): List<OperationEvent> = events.map { convertEvent(it) }

    private fun convertEvent(event: String): OperationEvent = when {
        event.startsWith(prefix = OperationIdentifier.ACCOUNT.identifier, startIndex = JSON_ROOT_POSITION) ->
            mapper.readValue(event, AccountOperationEvent::class.java) as OperationEvent
        else ->
            mapper.readValue(event, TransactionOperationEvent::class.java) as OperationEvent
    }
}
