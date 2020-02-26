package br.com.bank.account

import violation.OperationViolation

data class OperationResult(val account: Account?, val violations: List<OperationViolation>)