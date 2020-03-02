package br.com.bank.operation.account

import br.com.bank.operation.OperationIdentifier
import org.junit.Assert.*
import org.junit.Test

class AccountOperationEventTest {
    @Test
    fun `should convert operation event to operation`() {
        val subject = AccountOperationEvent(activeCard = true, availableLimit = 100)
        val accountOperation = subject.toOperation() as Account

        assertEquals(subject.activeCard, accountOperation.activeCard)
        assertEquals(subject.availableLimit, accountOperation.availableLimit)
        assertEquals(OperationIdentifier.ACCOUNT, accountOperation.getIdentifier())
    }
}