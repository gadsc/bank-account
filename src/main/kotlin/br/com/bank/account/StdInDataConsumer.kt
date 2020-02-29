package br.com.bank.account

class StdInDataConsumer(
        private val operationConsumer: OperationConsumer
) : DataConsumer {
    override fun batchProcessing(): List<OperationResult> = operationConsumer.consume()
            .map { OperationProcessorFactory.resolve(it).process(it.toOperation()) }
}
