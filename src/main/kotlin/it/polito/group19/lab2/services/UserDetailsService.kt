package it.polito.group19.lab2.services

import it.polito.group19.lab2.DTO.UserDetailsDTO
import it.polito.group19.lab2.domain.Customer
import it.polito.group19.lab2.domain.Rolename
import it.polito.group19.lab2.domain.User

interface UserDetailsService {

    fun addUser(customer: Customer, userDTO: UserDetailsDTO)

    fun addRole(role: Rolename, username: String)

    fun removeRole(role: Rolename, username: String)

    fun enable(username: String)

    fun disable(username: String)

    fun loadUserByUsername(username: String) : UserDetailsDTO
}
