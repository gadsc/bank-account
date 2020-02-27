package br.com.bank.account

import com.fasterxml.jackson.databind.ObjectMapper

class StdInDataStream(private val mapper: ObjectMapper, private val eventConverter: EventConverter) : DataStream {
    override fun start() {
        eventConverter.convertEvents(StdInReader.inputLoopRec()).map { EventProcessorFactory.process(it) }
    }
}