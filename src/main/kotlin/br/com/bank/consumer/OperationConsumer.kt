package br.com.bank.consumer

import br.com.bank.operation.OperationEvent
import br.com.bank.operation.OperationEventConverter

class OperationConsumer(
        private val operationEventConverter: OperationEventConverter,
        private val reader: Reader
) {
    fun consume(): List<OperationEvent> = operationEventConverter.convertEvents(reader.recursiveRead())
}