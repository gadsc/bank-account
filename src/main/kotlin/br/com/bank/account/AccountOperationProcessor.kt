package br.com.bank.account

class AccountOperationProcessor: OperationProcessor {
    override fun process(operation: Operation): OperationResult = AccountRepository.createAccount(operation as Account)
}
