package br.com.bank.operation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class OperationIdentifierTest {
    @Test
    fun `should return Account identifier for account event`() {
        val accountJson = "{\"account\": {\"active-card\": true, \"available-limit\": 100} }"

        assertEquals(OperationIdentifier.ACCOUNT, OperationIdentifier.find(accountJson))
    }

    @Test
    fun `should return Transaction identifier for transaction event`() {
        val transactionJson = "{\"transaction\": {\"merchant\": \"Burguer King\", \"amount\": 20, \"time\": \"2019-02-13T11:00:00.000Z\" }}"

        assertEquals(OperationIdentifier.TRANSACTION, OperationIdentifier.find(transactionJson))
    }

    @Test
    fun `should return null for not mapped event`() {
        val notMappedEvent = "{\"new-event\": {\"new-field\": \"New Value\" }}"

        assertNull(OperationIdentifier.find(notMappedEvent))
    }
}