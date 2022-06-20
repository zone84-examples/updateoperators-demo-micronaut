package tech.zone84.updateoperators.domain.shared

@JvmInline
value class UserId(val raw: String) {
    companion object {
        fun from(string: String) = UserId(string)
    }
}
