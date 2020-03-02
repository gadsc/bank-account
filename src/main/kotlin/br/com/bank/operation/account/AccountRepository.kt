package br.com.bank.operation.account

import br.com.bank.operation.OperationResult
import br.com.bank.operation.account.AccountValidations.accountAlreadyInitializedValidation

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

    fun clearActiveAccount() {
        createdAccount = null
    }

    fun updateActiveAccount(account: Account): OperationResult {
        createdAccount = account
        return OperationResult(account = createdAccount, violations = emptyList())
    }
}
