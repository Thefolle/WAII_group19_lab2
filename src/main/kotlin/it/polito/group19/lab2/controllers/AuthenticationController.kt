package it.polito.group19.lab2.controllers

import it.polito.group19.lab2.dto.RegisterDTO
import it.polito.group19.lab2.services.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthenticationController(private val userDetailsServiceImpl: UserDetailsServiceImpl){

    @PostMapping("/register")
    fun register (@RequestBody registerDTO: RegisterDTO): ResponseEntity<String>{

        userDetailsServiceImpl.addUser(registerDTO)

        return ResponseEntity.status(HttpStatus.CREATED)
                                    .body("User registered successfully." +
                                            " You will receive an email shortly to confirm your registration.")
    }

}