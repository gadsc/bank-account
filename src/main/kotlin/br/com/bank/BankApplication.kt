package br.com.bank

import br.com.bank.infra.CustomObjectMapper
import br.com.bank.infra.DataConsumer
import br.com.bank.operation.OperationEventConverter
import br.com.bank.operation.OperationResultOutput
import br.com.bank.operation.consumer.OperationConsumer
import br.com.bank.operation.consumer.stdin.StdInBatchDataConsumer
import br.com.bank.operation.consumer.stdin.StdInReader

fun main(args: Array<String>) {
    println("Operations in a specific account!\nAdd empty line to stop input...")
    val mapper = CustomObjectMapper.mapper
    val operationConsumer =
            OperationConsumer(operationEventConverter = OperationEventConverter(mapper), reader = StdInReader())
    val dataConsumer: DataConsumer = StdInBatchDataConsumer(operationConsumer = operationConsumer)

    dataConsumer.process().forEach { println(mapper.writeValueAsString(OperationResultOutput.from(it))) }
}
