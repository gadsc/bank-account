package br.com.bank.account

interface BankEvent {
    fun toOperation(): Operation
}