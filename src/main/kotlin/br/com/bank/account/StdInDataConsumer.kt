package br.com.bank.account

class StdInDataConsumer(
    private val operationConsumer: OperationConsumer
) : DataConsumer {
    override fun batchProcessing() {
        operationConsumer.consume()
            .forEach { OperationProcessorFactory.resolve(it).process(it.toOperation()) }
    }
}
