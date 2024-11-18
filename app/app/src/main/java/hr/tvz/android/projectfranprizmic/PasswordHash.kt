package hr.tvz.android.projectfranprizmic

import java.security.MessageDigest

class PasswordHash {
    companion object {
        fun hashPassword(password: String): String {
            var bytes = MessageDigest
                .getInstance("SHA-256")
                .digest(password.toByteArray())

            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}