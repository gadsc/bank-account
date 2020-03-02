package br.com.bank.operation.account.transaction

import br.com.bank.operation.OperationIdentifier
import br.com.bank.operation.objectMother.TransactionOperationEventObjectMother
import org.junit.Assert.assertEquals
import org.junit.Test

class TransactionOperationEventTest {
    @Test
    fun `should convert operation event to operation`() {
        val subject = TransactionOperationEventObjectMother.build()
        val transactionOperation = subject.toOperation() as Transaction

        assertEquals(subject.merchant, transactionOperation.merchant)
        assertEquals(subject.amount, transactionOperation.amount)
        assertEquals(subject.time, transactionOperation.time)
        assertEquals(OperationIdentifier.TRANSACTION, transactionOperation.getIdentifier())
    }
}
