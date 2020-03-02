package br.com.bank.operation

import br.com.bank.operation.OperationIdentifierKey.OPERATION_IDENTIFIER_ROOT_KEY

enum class OperationIdentifier(val identifier: String) {
    ACCOUNT("account"),
    TRANSACTION("transaction");

    companion object {
        private const val JSON_ROOT_POSITION = 2

        fun find(event: String): OperationIdentifier? = with(event.replace(regex = OPERATION_IDENTIFIER_ROOT_KEY, replacement = "")) {
            when {
                startsWith(prefix = "${ACCOUNT.identifier}", startIndex = JSON_ROOT_POSITION) -> ACCOUNT
                startsWith(prefix = "${TRANSACTION.identifier}", startIndex = JSON_ROOT_POSITION) -> TRANSACTION
                else -> null
            }
        }
    }
}

private object OperationIdentifierKey {
    val OPERATION_IDENTIFIER_ROOT_KEY = Regex(pattern = "\\s*(?=\"(${OperationIdentifier.ACCOUNT.identifier}|${OperationIdentifier.TRANSACTION.identifier})\":)")
}
