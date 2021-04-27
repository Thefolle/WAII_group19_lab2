package it.polito.group19.lab2.services

import it.polito.group19.lab2.domain.EmailVerificationToken
import it.polito.group19.lab2.dto.EmailVerificationTokenDTO
import it.polito.group19.lab2.repositories.EmailVerificationTokenRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
@Transactional
class NotificationServiceImpl(val emailVerificationTokenRepository: EmailVerificationTokenRepository): NotificationService {
    override fun createToken(username: String): EmailVerificationTokenDTO {
        val token = EmailVerificationToken(
                null,
                expiryDate = LocalDateTime.now().plusDays(1),
                username = username
        )
        emailVerificationTokenRepository.save(token)
        return token.toDTO()
    }

    override fun getToken(token: String): EmailVerificationTokenDTO {
        val optionalToken = emailVerificationTokenRepository.findByToken(token)
        if (optionalToken.isEmpty) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found!")
        return optionalToken.get().toDTO()
    }

    override fun tokenNotExpired(token: String): Boolean{
        val t = getToken(token)
        return !t.expiryDate.isBefore(LocalDateTime.now())
    }
}