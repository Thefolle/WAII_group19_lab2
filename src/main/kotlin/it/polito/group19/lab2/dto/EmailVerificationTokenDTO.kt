package it.polito.group19.lab2.dto

import java.time.LocalDateTime

class EmailVerificationTokenDTO(
        var token: String,
        var expiryDate: LocalDateTime,
        var username: String
) {
}
