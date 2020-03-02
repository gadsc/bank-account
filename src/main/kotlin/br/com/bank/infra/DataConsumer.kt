package br.com.bank.infra

import br.com.bank.operation.OperationResult

interface DataConsumer {
    fun process(): List<OperationResult>
}