package br.com.bank.operation.consumer.stdin

import br.com.bank.operation.consumer.OperationConsumer
import br.com.bank.operation.processor.OperationProcessorFactory
import br.com.bank.operation.OperationResult
import br.com.bank.infra.DataConsumer

class StdInDataConsumer(
        private val operationConsumer: OperationConsumer
) : DataConsumer {
    override fun batchProcessing(): List<OperationResult> = operationConsumer.consume()
            .map { OperationProcessorFactory.resolve(it).process(it.toOperation()) }
}
