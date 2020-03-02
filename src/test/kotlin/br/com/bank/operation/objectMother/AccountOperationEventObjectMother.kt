package br.com.bank.operation.objectMother

import br.com.bank.operation.account.Account
import br.com.bank.operation.account.AccountOperationEvent
import br.com.bank.operation.account.transaction.Transaction

object AccountOperationEventObjectMother {
    fun build(activeCard: Boolean = true, availableLimit: Long = 100): AccountOperationEvent = AccountOperationEvent(
            activeCard = activeCard, availableLimit = availableLimit
    )
}