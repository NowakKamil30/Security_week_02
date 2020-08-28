package pl.akademiaspring.security_week_01.exceptions

import java.lang.RuntimeException

class UserExistsException(override val message: String? = "user exists") : RuntimeException() {
}