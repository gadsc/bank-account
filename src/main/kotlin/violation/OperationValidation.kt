package violation

object OperationValidation {
    //    val violations: List<OperationViolation>
    fun findViolation(isViolated: () -> Boolean, violation: OperationViolation): OperationViolation? =
            if (isViolated()) violation else null
}

//fun OperationValidation.findViolation(isViolated: () -> Boolean, violation: OperationViolation): OperationViolation? =
//        if (isViolated()) violation else null

