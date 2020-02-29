package violation

class CardNotActiveViolation(
        override val reason: String = "card-not-active",
        override val message: String = "Card not active"
) : OperationViolation