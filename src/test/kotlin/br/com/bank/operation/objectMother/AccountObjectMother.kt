package br.com.bank.operation.objectMother

import br.com.bank.operation.account.Account
import br.com.bank.operation.account.transaction.Transaction

object AccountObjectMother {
    fun build(activeCard: Boolean = true, availableLimit: Long = 100, transactions: List<Transaction> = emptyList()): Account =
            Account(
                    activeCard = activeCard, availableLimit = availableLimit, transactions = transactions
            )
}