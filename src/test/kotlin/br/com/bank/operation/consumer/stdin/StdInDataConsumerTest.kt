package br.com.bank.operation.consumer.stdin

import br.com.bank.operation.consumer.OperationConsumer
import br.com.bank.operation.objectMother.AccountOperationEventObjectMother
import br.com.bank.operation.objectMother.TransactionOperationEventObjectMother
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StdInDataConsumerTest {
    private lateinit var subject: StdInDataConsumer
    private val accountEvent = AccountOperationEventObjectMother.build()
    private val transactionEvent = TransactionOperationEventObjectMother.build()

    @Before
    fun init() {
        val events = listOf(accountEvent, transactionEvent)

        val operationConsumer: OperationConsumer = mockk(relaxed = true)

        every { operationConsumer.consume() } returns events

        subject = StdInDataConsumer(operationConsumer)
    }

    @Test
    fun `should return executed operations`() {
        val results = subject.batchProcessing()

        assertEquals(2, results.size)
        assertEquals(accountEvent.activeCard, results[0].account?.activeCard)
        assertEquals(accountEvent.availableLimit, results[0].account?.availableLimit)
        assertEquals(accountEvent.activeCard, results[1].account?.activeCard)
        assertEquals(accountEvent.availableLimit - transactionEvent.amount, results[1].account?.availableLimit)
    }
}