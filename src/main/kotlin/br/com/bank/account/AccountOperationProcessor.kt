package br.com.bank.account

class AccountOperationProcessor: OperationProcessor {
    override fun process(operation: Operation): OperationResult {
        val result = AccountRepository.createAccount2(operation as Account)
        println(result)
        return result
    }

}
