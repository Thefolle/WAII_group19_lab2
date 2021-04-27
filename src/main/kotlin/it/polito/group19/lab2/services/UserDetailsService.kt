package it.polito.group19.lab2.services

import it.polito.group19.lab2.dto.RegisterDTO
import it.polito.group19.lab2.dto.UserDetailsDTO
import it.polito.group19.lab2.domain.Rolename

interface UserDetailsService {

//    fun addUser(customer: Customer, userDTO: UserDetailsDTO)
    fun addUser(registerDTO: RegisterDTO)

    fun addRole(role: Rolename, username: String)

    fun removeRole(role: Rolename, username: String)

    fun enable(username: String)

    fun disable(username: String)

    fun loadUserByUsername(username: String) : UserDetailsDTO

    fun registrationConfirm(token: String): UserDetailsDTO
}
