package it.polito.group19.lab2.controllers

import it.polito.group19.lab2.DTO.RegisterDTO
import it.polito.group19.lab2.services.UserDetailsServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthenticationController(private val userDetailsServiceImpl: UserDetailsServiceImpl){

    @PostMapping("/register")
    fun register (@RequestBody reg: RegisterDTO): ResponseEntity<String>{
        if(reg.password == reg.confirmPassword){
            userDetailsServiceImpl.addUser(reg)
            return ResponseEntity.status(HttpStatus.CREATED)
                                    .body("User registered successfully")
        }

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                                .body("Password and confirmPassword doesn't match")
    }
}