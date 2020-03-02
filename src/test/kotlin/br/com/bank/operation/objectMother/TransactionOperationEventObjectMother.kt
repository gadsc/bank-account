package br.com.bank.operation.objectMother

import br.com.bank.operation.account.transaction.TransactionOperationEvent
import java.time.ZonedDateTime

object TransactionOperationEventObjectMother {
    fun build(merchant: String = "Burger King", amount: Long = 20, time: ZonedDateTime = ZonedDateTime.now()): TransactionOperationEvent =
            TransactionOperationEvent(
                    merchant = merchant,
                    amount = amount,
                    time = time
            )
}
