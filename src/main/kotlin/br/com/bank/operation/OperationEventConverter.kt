package br.com.bank.operation

import br.com.bank.infra.CustomObjectMapper
import br.com.bank.operation.account.AccountOperationEvent
import br.com.bank.operation.account.transaction.TransactionOperationEvent
import com.fasterxml.jackson.databind.ObjectMapper

class OperationEventConverter(
        private val mapper: ObjectMapper
) {
    private val accounts: MutableMap<OperationIdentifier, List<AccountOperationEvent>> = mutableMapOf()
    private val transactions: MutableMap<OperationIdentifier, List<TransactionOperationEvent>> = mutableMapOf()

    companion object {
        private const val JSON_ROOT_POSITION = 2
    }

    fun convertEvents(events: List<String>): List<OperationEvent> = mapEvents(events).let {
        accounts.flatMap { it.value } + transactions.flatMap { it.value.sortedBy { transaction -> transaction.time } }
    }
    
    private fun mapEvents(events: List<String>) {
        events.forEach { event ->
            when {
                event.startsWith(prefix = OperationIdentifier.ACCOUNT.identifier, startIndex = JSON_ROOT_POSITION) ->
                    accounts[OperationIdentifier.ACCOUNT] =
                            accounts.getOrDefault(key = OperationIdentifier.ACCOUNT, defaultValue = emptyList()) +
                                    mapper.readValue(event, AccountOperationEvent::class.java)
                else ->
                    transactions[OperationIdentifier.TRANSACTION] =
                            transactions.getOrDefault(key = OperationIdentifier.TRANSACTION, defaultValue = emptyList()) +
                                    mapper.readValue(event, TransactionOperationEvent::class.java)
            }
        }
    }
}
