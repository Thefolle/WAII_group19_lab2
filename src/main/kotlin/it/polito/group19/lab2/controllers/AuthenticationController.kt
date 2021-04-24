package it.polito.group19.lab2.controllers

import it.polito.group19.lab2.dto.RegisterDTO
import it.polito.group19.lab2.dto.UserDetailsDTO
import it.polito.group19.lab2.services.NotificationServiceImpl
import it.polito.group19.lab2.services.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/auth")
class AuthenticationController(private val userDetailsServiceImpl: UserDetailsServiceImpl,
                               private val notificationServiceImpl: NotificationServiceImpl ){

    @PostMapping("/register")
    fun register (@RequestBody registerDTO: RegisterDTO): ResponseEntity<String>{

        userDetailsServiceImpl.addUser(registerDTO)

        return ResponseEntity.status(HttpStatus.CREATED)
                                    .body("User registered successfully." +
                                            " You will receive an email shortly to confirm your registration.")
    }

    @GetMapping("/auth/registrationConfirm?token={token}")
    fun registrationConfirm(@PathVariable("token") token: String): ResponseEntity<UserDetailsDTO>{
        val verifyToken = notificationServiceImpl.getToken(token)
        if (!notificationServiceImpl.tokenNotExpired(verifyToken.token)) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Token not Expired!")
        userDetailsServiceImpl.enable(verifyToken.username)
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsServiceImpl.loadUserByUsername(verifyToken.username))
    }


}
