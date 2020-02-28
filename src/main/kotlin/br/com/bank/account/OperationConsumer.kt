package br.com.bank.account

class OperationConsumer(
    private val operationEventConverter: OperationEventConverter,
    private val reader: Reader
) {
    fun consume(): List<OperationEvent> = operationEventConverter.convertEvents(reader.recursiveRead())
}