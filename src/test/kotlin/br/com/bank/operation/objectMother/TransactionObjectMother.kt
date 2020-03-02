package br.com.bank.operation.objectMother

import br.com.bank.operation.account.transaction.Transaction
import java.time.ZonedDateTime

object TransactionObjectMother {
    fun build(merchant: String = "Burger King", amount: Long = 20, time: ZonedDateTime = ZonedDateTime.now()): Transaction =
            Transaction(
                    merchant = merchant,
                    amount = amount,
                    time = time
            )

    fun buildMany(amountOfTransactions: Int = 3, repeated: Boolean = false): List<Transaction> =
            (1..amountOfTransactions).mapIndexed { index, _ ->
                build(
                        merchant = if (repeated) "Burger King" else "Burger King - ${index.toLong()}"
                )
            }
}