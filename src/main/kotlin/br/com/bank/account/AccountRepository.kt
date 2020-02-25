package br.com.bank.account

class AccountRepository {
    private var createdAccount: Account? = null

    fun createAccount(account: Account) = if (createdAccount == null) {
        createdAccount = account
    } else {
        throw RuntimeException("Account already exists")
    }

    fun findActiveAccount(): Account? = with(createdAccount) {
        when {
            this == null -> throw RuntimeException("Account not initialized")
            !this.activeCard -> throw RuntimeException("Card not active")
            else -> this
        }
    }

    fun updateActiveAccount(account: Account): Account {
        createdAccount = account
        return createdAccount!!
    }
}