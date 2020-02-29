package br.com.bank.account

import br.com.bank.account.AccountValidations.accountAlreadyInitializedValidation

object AccountRepository {
    var createdAccount: Account? = null
        private set

    fun createAccount(account: Account): OperationResult = accountAlreadyInitializedValidation(createdAccount)
            .let {
                if (it == null) {
                    createdAccount = account
                    OperationResult(createdAccount!!, emptyList())
                } else OperationResult(
                        account = account,
                        violations = listOfNotNull(it)
                )
            }

//    fun find(): Account? = createdAccount

    fun updateActiveAccount(account: Account): Account {
        createdAccount = account
        return createdAccount!!
    }
}