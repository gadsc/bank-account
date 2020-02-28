package br.com.bank.account

class AccountEventProcessor: EventProcessor {
    override fun process(event: Operation): OperationResult = AccountRepository.createAccount(event as Account)

}
