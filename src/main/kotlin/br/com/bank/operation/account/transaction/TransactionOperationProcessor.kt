package br.com.bank.operation.account.transaction

import br.com.bank.operation.Operation
import br.com.bank.operation.OperationResult
import br.com.bank.operation.account.AccountRepository.createdAccount
import br.com.bank.operation.processor.OperationProcessor

class TransactionOperationProcessor : OperationProcessor {
    override fun process(operation: Operation): OperationResult = (operation as Transaction).commit(createdAccount)
}
