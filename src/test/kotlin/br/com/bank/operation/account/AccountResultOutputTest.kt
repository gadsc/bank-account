package br.com.bank.operation.account

import br.com.bank.operation.objectMother.AccountObjectMother
import org.junit.Assert.*
import org.junit.Test

class AccountResultOutputTest {
    @Test
    fun `should create result output from operation`() {
        val account = AccountObjectMother.build()
        val subject = AccountResultOutput.from(account)

        assertEquals(account.activeCard, subject.activeCard)
        assertEquals(account.availableLimit, subject.availableLimit)
    }
}