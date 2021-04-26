package it.polito.group19.lab2.security

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import it.polito.group19.lab2.dto.UserDetailsDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SignatureException
import java.util.*


@Component
class JwtUtils {


    @Value("\${application.jwt.jwtExpirationMs}")
    private val jwtExpirationMs: Long = 1800000

    @Value("\${application.jwt.jwtSecret}")
    private val jwtSecret: String = "AFDRQMmyH6bxiFpTnkxDRqrZXQhVANRCvEIqFz7oJaJHvNkhD7yOLC93vOl4wne3"

    private val keyBytes = Decoders.BASE64.decode(jwtSecret)
    private val key: Key = Keys.hmacShaKeyFor(keyBytes)

    fun generateJwtToken(authentication: Authentication): String {

        val userPrincipal = authentication.principal as UserDetailsDTO
        val issuer = userPrincipal.uid.toString()

        return Jwts.builder()
            .setIssuedAt(Date())
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(key)
            .claim("username", userPrincipal.uname)
            .claim("roles", userPrincipal.roles)
            .compact()
    }


    fun validateJwtToken(authToken: String): Boolean {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(authToken)
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


    fun getDetailsFromJwtToken(authToken: String): UserDetailsDTO {

        val body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken).body
        val username = body["username"].toString()
        val roles = body["roles"].toString()

        return  UserDetailsDTO(null, username,"","", false, roles)
    }
}