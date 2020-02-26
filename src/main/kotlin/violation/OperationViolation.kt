package violation

interface OperationViolation {
    val reason: String
    val message: String
}