package br.com.bank.account

class StdInDataConsumer(private val eventConverter: EventConverter) : DataConsumer {
    override fun batchProcessing() {
        eventConverter.convertEvents(StdInReader.inputLoopRec())
            .map { EventProcessorFactory.resolve(it).process(it.toOperation()) }
    }
}