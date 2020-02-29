package br.com.bank.account

interface DataConsumer {
    fun batchProcessing(): List<OperationResult>
}