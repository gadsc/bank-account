package br.com.bank.account

enum class OperationIdentifier(val identifier: String) {
    ACCOUNT("account"),
    TRANSACTION("transaction")
}