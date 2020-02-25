package br.com.bank.account

object AccountRepository {
    private var createdAccount: Account? = null

    fun createAccount(account: Account): Pair<Account, List<Violation>> = if (createdAccount == null) {
        createdAccount = account
        Pair(createdAccount!!, emptyList())
    } else {
        Pair(createdAccount!!, listOf(Violation(key = "account-already-initialized", message = "Account already exists")))
    }

    fun findActiveAccount(): Account? = createdAccount

    fun updateActiveAccount(account: Account): Account {
        createdAccount = account
        return createdAccount!!
    }
}