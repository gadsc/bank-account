package br.com.bank.account

import violation.OperationValidations

object AccountRepository {
    private var createdAccount: Account? = null

//    fun createAccount(account: Account): OperationResult = if (createdAccount == null) {
//        createdAccount = account
//        OperationResult(createdAccount!!, emptyList())
//    } else {
//        OperationResult(createdAccount!!, listOf(AccountAlreadyInitializedViolation()))
//    }

    fun createAccount2(account: Account): OperationResult = Account.accountAlreadyInitializedValidation(createdAccount)
            .let {
                if (it == null) {
                    createdAccount = account
                    OperationResult(createdAccount!!, emptyList())
                } else OperationResult(
                        account = account,
                        violations = listOfNotNull(it)
                )
            }

    fun find(): Account? = createdAccount

    fun updateActiveAccount(account: Account): Account {
        createdAccount = account
        return createdAccount!!
    }
}