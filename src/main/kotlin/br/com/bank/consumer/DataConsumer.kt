package br.com.bank.consumer

import br.com.bank.operation.OperationResult

interface DataConsumer {
    fun batchProcessing(): List<OperationResult>
}