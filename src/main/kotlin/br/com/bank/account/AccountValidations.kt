package br.com.bank.account

import violation.*

object AccountValidations {
    fun accountAlreadyInitializedValidation(account: Account?) =
            OperationValidation.hasViolation({ account != null }, AccountAlreadyInitializedViolation())

    fun insufficientLimitViolation(account: Account, transaction: Transaction): OperationViolation? =
            OperationValidation.hasViolation({ account.availableLimit < transaction.amount }, InsufficientLimitViolation())

    fun readyForTransaction(account: Account?) =
            accountNotInitializedValidation(account = account) ?: cardNotActiveValidation(account = account)

    private fun accountNotInitializedValidation(account: Account?) =
            OperationValidation.hasViolation({ account == null }, AccountNotInitializedViolation())

    private fun cardNotActiveValidation(account: Account?) =
            OperationValidation.hasViolation({ account != null && !account.activeCard }, CardNotActiveViolation())
}