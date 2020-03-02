package br.com.bank.operation.account

import br.com.bank.operation.account.AccountValidations.accountAlreadyInitializedValidation
import br.com.bank.operation.OperationResult

object AccountRepository {
    var createdAccount: Account? = null
        private set

    fun createAccount(account: Account): OperationResult = accountAlreadyInitializedValidation(createdAccount)
            .let {
                it?.let { OperationResult(account = account, violation = it) } ?: run {
                    createdAccount = account
                    OperationResult(createdAccount!!, emptyList())
                }
            }

    fun updateActiveAccount(account: Account): OperationResult {
        createdAccount = account
        return OperationResult(account = createdAccount, violations = emptyList())
    }
}