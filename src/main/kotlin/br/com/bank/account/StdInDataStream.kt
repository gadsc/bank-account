package br.com.bank.account

class StdInDataStream(private val eventConverter: EventConverter) : DataStream {
    override fun startProcessing(events: List<String>) {
        eventConverter.convertEvents(events).map { EventProcessorFactory.process(it) }
    }
}