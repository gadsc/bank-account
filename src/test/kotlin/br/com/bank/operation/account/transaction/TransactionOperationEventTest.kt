package br.com.bank.operation.account.transaction

import br.com.bank.operation.OperationIdentifier
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.ZonedDateTime

class TransactionOperationEventTest {
    @Test
    fun `should convert operation event to operation`() {
        val subject = TransactionOperationEvent(merchant = "Burguer King", amount = 100, time = ZonedDateTime.now())
        val transactionOperation = subject.toOperation() as Transaction

        assertEquals(subject.merchant, transactionOperation.merchant)
        assertEquals(subject.amount, transactionOperation.amount)
        assertEquals(subject.time, transactionOperation.time)
        assertEquals(OperationIdentifier.TRANSACTION, transactionOperation.getIdentifier())
    }
}
