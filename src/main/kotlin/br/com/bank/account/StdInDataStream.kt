package br.com.bank.account

import com.fasterxml.jackson.databind.ObjectMapper

class StdInDataStream(private val mapper: ObjectMapper) : DataStream {
    override fun start() {
        StdInReader.inputLoopRec().map {
            if (it.contains(OperationIdentifier.ACCOUNT.identifier)) {
                mapper.readValue(it, AccountRequest::class.java) as BankEvent
            } else {
                mapper.readValue(it, TransactionRequest::class.java) as BankEvent
            }
        }.map { EventProcessorFactory.process(it) }
    }
}