package br.com.bank.account

interface DataStream {
    fun startProcessing(events: List<String>)
}