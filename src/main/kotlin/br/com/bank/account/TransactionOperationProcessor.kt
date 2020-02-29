package br.com.bank.account

import br.com.bank.account.AccountRepository.createdAccount

class TransactionOperationProcessor : OperationProcessor {
    override fun process(operation: Operation): OperationResult = (operation as Transaction).commit(createdAccount)
}
