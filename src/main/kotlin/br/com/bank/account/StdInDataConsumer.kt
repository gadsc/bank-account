package br.com.bank.account

class StdInDataConsumer(private val operationEventConverter: OperationEventConverter) : DataConsumer {
    override fun batchProcessing() {
        operationEventConverter.convertEvents(StdInReader.inputLoopRec())
            .map { OperationProcessorFactory.resolve(it).process(it.toOperation()) }
    }
}