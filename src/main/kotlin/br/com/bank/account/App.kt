/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package br.com.bank.account

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

class App {
    val greeting: String
        get() {
            return "Operations in a specific account!\nPress enter to stop input..."
        }
}

fun main(args: Array<String>) {
    println(App().greeting)
    val mapper = getCustomJacksonMapper()

    while (true) getMappedEvents(inputLoopRec(), mapper)
        .map { EventProcessorFactory.process(it) }
}

private fun getCustomJacksonMapper(): ObjectMapper =
    ObjectMapper().registerModule(KotlinModule())
        .registerModule(JavaTimeModule())
        .enable(SerializationFeature.WRAP_ROOT_VALUE)
        .enable(DeserializationFeature.UNWRAP_ROOT_VALUE)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

enum class OperationIdentifier(val identifier: String) {
    ACCOUNT("account"),
    TRANSACTION("transaction")
}

private fun getMappedEvents(
    events: List<String>,
    mapper: ObjectMapper
): List<BankEvent> {
    return events.map {
        if (it.contains(OperationIdentifier.ACCOUNT.identifier)) {
            mapper.readValue(it, AccountRequest::class.java) as BankEvent
        } else {
            mapper.readValue(it, TransactionRequest::class.java) as BankEvent
        }
    }
}

//fun inputLoop(exitCode: String = ""): List<String> {
//    val read = readLine()!!
//    val events: MutableList<String> = mutableListOf()
//
//    if (read == exitCode) {
//        println("Exiting with code $read bye bye!")
//    } else {
//        events.add(read)
//        events.addAll(inputLoop(exitCode = exitCode))
//    }
//
//    return events.toList()
//}

tailrec fun inputLoopRec(events: List<String> = emptyList(), exitCode: String = ""): List<String> =
    readLine()?.let {
        if (it == exitCode) {
            events
        } else {
            inputLoopRec(events = events + it, exitCode = exitCode)
        }
    } ?: emptyList()