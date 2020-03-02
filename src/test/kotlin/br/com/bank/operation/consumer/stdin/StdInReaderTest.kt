package br.com.bank.operation.consumer.stdin

import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Test

class StdInReaderTest {
    @Test
    fun `should read the input until read the exit code`() {
        val accountJson = "{\"account\": {\"active-card\": true, \"available-limit\": 100} }"
        val transactionJson = "{\"transaction\": {\"merchant\": \"Burguer King\", \"amount\": 20, \"time\": \"2019-02-13T11:00:00.000Z\" }}"
        val exitCode = ""

        mockkStatic("kotlin.io.ConsoleKt")
        every { readLine() } returns accountJson andThen transactionJson andThen exitCode

        val subject = StdInReader()

        assertEquals(listOf(accountJson, transactionJson), subject.recursiveRead(exitCode = exitCode))
    }
}