package br.com.bank.operation.processor

import br.com.bank.operation.Operation
import br.com.bank.operation.OperationEvent
import br.com.bank.operation.account.AccountOperationProcessor
import br.com.bank.operation.account.transaction.TransactionOperationProcessor
import br.com.bank.operation.objectMother.AccountOperationEventObjectMother
import br.com.bank.operation.objectMother.TransactionOperationEventObjectMother
import org.assertj.core.api.Assertions
import org.junit.Assert.assertTrue
import org.junit.Test

class OperationProcessorFactoryTest {
    @Test
    fun `should return account operation processor to process account operation event`() {
        val result = OperationProcessorFactory.resolve(AccountOperationEventObjectMother.build())

        assertTrue(result is AccountOperationProcessor)
    }

    @Test
    fun `should return transaction operation processor to process transaction operation event`() {
        val result = OperationProcessorFactory.resolve(TransactionOperationEventObjectMother.build())

        assertTrue(result is TransactionOperationProcessor)
    }

    @Test
    fun `should throw RuntimeException when not found event`() {
        Assertions.assertThatExceptionOfType(RuntimeException::class.java)
                .isThrownBy { OperationProcessorFactory.resolve(TestOperationEvent("test")) }
                .withMessage("Not expected message")
    }

    private data class TestOperationEvent(val name: String): OperationEvent {
        override fun toOperation(): Operation {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}