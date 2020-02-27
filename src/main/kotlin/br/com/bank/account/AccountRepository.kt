package br.com.bank.account

import violation.AccountAlreadyInitializedViolation

object AccountRepository {
    private var createdAccount: Account? = null

    fun createAccount(account: Account?): OperationResult = if (createdAccount == null) {
        createdAccount = account
        OperationResult(createdAccount!!, emptyList())
    } else {
        OperationResult(createdAccount!!, listOf(AccountAlreadyInitializedViolation()))
    }

    fun find(): Account? = createdAccount

    fun updateActiveAccount(account: Account): Account {
        createdAccount = account
        return createdAccount!!
    }
}