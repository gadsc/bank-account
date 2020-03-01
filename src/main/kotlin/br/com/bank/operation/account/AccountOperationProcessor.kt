package br.com.bank.operation.account

import br.com.bank.operation.Operation
import br.com.bank.operation.OperationResult
import br.com.bank.operation.processor.OperationProcessor

class AccountOperationProcessor: OperationProcessor {
    override fun process(operation: Operation): OperationResult = AccountRepository.createAccount(operation as Account)
}
