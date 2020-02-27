package br.com.bank.account

class AccountEventProcessor: EventProcessor<Account> {
    override fun process(event: Account): OperationResult = AccountRepository.createAccount(event)

}
