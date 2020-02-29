package violation

class HighFrequencyViolation(
        override val reason: String = "high-frequency-small-interval",
        override val message: String = "High frequency small interval"
) : OperationViolation