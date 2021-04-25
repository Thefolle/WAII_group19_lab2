package it.polito.group19.lab2.security

import io.jsonwebtoken.*
import it.polito.group19.lab2.dto.UserDetailsDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.security.SignatureException
import java.util.*

@Component
class JwtUtils {

    @Value("#{application.jwt.jwtExpirationMs}")
    private val jwtExpirationMs: Int = 0

    @Value("#{application.jwt.jwtSecret}")
    private val jwtSecret: String = ""

    fun generateJwtToken(authentication: Authentication): String {

        val userPrincipal = authentication.principal as UserDetailsDTO
        val issuer = userPrincipal.uid.toString()

        return Jwts.builder()
            .setSubject(userPrincipal.uname)
            .setIssuedAt(Date())
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.ES512, jwtSecret)
            .compact()
    }

    fun validateJwtToken(authToken: String): Boolean {
        try {
            Jwts.parserBuilder().requireAudience(jwtSecret).build().parse(authToken)
            return true
        } catch (e: SignatureException){
            throw e
        } catch (e: MalformedJwtException){
            throw e
        } catch (e: ExpiredJwtException){
            throw e
        } catch (e: UnsupportedJwtException){
            throw e
        } catch (e: IllegalArgumentException){
            throw e
        }

        return false
    }

//    fun getDetailsFromJwtToken(authToken: String): UserDetailsDTO {
//        return
//    }
}