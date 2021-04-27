package it.polito.group19.lab2.services

import it.polito.group19.lab2.dto.EmailVerificationTokenDTO

interface NotificationService {

    fun createToken(username: String): EmailVerificationTokenDTO

    fun getToken(token: String): EmailVerificationTokenDTO

    fun tokenNotExpired(token: String): Boolean

}
