package br.com.bank.operation.account

import br.com.bank.operation.account.AccountValidations.accountAlreadyInitializedValidation
import br.com.bank.operation.OperationResult

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