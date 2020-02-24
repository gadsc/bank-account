package br.com.bank.account

import java.time.ZonedDateTime

data class Transaction(val merchant: String, val amount: Long, val time: ZonedDateTime) {
    companion object {
        fun from(transactionRequest: TransactionRequest) = Transaction(
            merchant = transactionRequest.merchant,
            amount = transactionRequest.amount,
            time = transactionRequest.time
        )
    }
}