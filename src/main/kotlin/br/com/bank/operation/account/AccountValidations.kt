package br.com.bank.operation.account

import br.com.bank.operation.account.transaction.Transaction
import br.com.bank.operation.validation.OperationValidation
import br.com.bank.operation.validation.violation.*

object AccountValidations {
    fun accountAlreadyInitializedValidation(account: Account?) =
            OperationValidation.hasViolation({ account != null }, AccountAlreadyInitializedViolation())

    fun insufficientLimitValidation(account: Account, transaction: Transaction): OperationViolation? =
            OperationValidation.hasViolation({ account.availableLimit < transaction.amount }, InsufficientLimitViolation())

    fun readyForTransaction(account: Account?) =
            accountNotInitializedValidation(account = account)
                    ?: cardNotActiveValidation(account = account)

    private fun accountNotInitializedValidation(account: Account?) =
            OperationValidation.hasViolation({ account == null }, AccountNotInitializedViolation())

    private fun cardNotActiveValidation(account: Account?) =
            OperationValidation.hasViolation({ account != null && !account.activeCard }, CardNotActiveViolation())
}
