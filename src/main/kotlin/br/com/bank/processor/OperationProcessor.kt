package br.com.bank.processor

import br.com.bank.operation.Operation
import br.com.bank.operation.OperationResult

interface OperationProcessor {
    fun process(operation: Operation): OperationResult
}