package br.com.bank.operation

import br.com.bank.operation.OperationIdentifier.ACCOUNT
import br.com.bank.operation.OperationIdentifier.TRANSACTION
import br.com.bank.operation.account.AccountOperationEvent
import br.com.bank.operation.account.transaction.TransactionOperationEvent
import com.fasterxml.jackson.databind.ObjectMapper

class OperationEventConverter(
        private val mapper: ObjectMapper
) {
    fun convertEvents(events: List<String>): List<OperationEvent> {
        val accounts: MutableMap<OperationIdentifier, List<AccountOperationEvent>> = mutableMapOf()
        val transactions: MutableMap<OperationIdentifier, List<TransactionOperationEvent>> = mutableMapOf()

        events.forEach { event -> eventClassifier(event, accounts, transactions) }

        return accounts.flatMap { it.value } + transactions.flatMap { it.value.sortedBy { transaction -> transaction.time } }
    }

    private fun eventClassifier(event: String, accounts: MutableMap<OperationIdentifier, List<AccountOperationEvent>>, transactions: MutableMap<OperationIdentifier, List<TransactionOperationEvent>>) {
        when (OperationIdentifier.find(event)) {
            ACCOUNT -> mapEvent(event, ACCOUNT, AccountOperationEvent::class.java, accounts)
            TRANSACTION -> mapEvent(event, TRANSACTION, TransactionOperationEvent::class.java, transactions)
            else -> throw NotImplementedError("Event not mapped yet")
        }
    }

    private fun <T> mapEvent(event: String, operationIdentifier: OperationIdentifier, clazz: Class<T>, map: MutableMap<OperationIdentifier, List<T>>) {
        map[operationIdentifier] =
                map.getOrDefault(key = operationIdentifier, defaultValue = emptyList()) + mapper.readValue(event, clazz)
    }
}
