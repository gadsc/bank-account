package br.com.bank.consumer.stdin

import br.com.bank.consumer.OperationConsumer
import br.com.bank.processor.OperationProcessorFactory
import br.com.bank.operation.OperationResult
import br.com.bank.consumer.DataConsumer

class StdInDataConsumer(
        private val operationConsumer: OperationConsumer
) : DataConsumer {
    override fun batchProcessing(): List<OperationResult> = operationConsumer.consume()
            .map { OperationProcessorFactory.resolve(it).process(it.toOperation()) }
}
