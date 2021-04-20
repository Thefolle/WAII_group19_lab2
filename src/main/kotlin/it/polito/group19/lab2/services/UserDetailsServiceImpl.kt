package it.polito.group19.lab2.services

import it.polito.group19.lab2.DTO.UserDetailsDTO
import it.polito.group19.lab2.domain.Customer
import it.polito.group19.lab2.domain.Rolename
import it.polito.group19.lab2.domain.User
import it.polito.group19.lab2.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class UserDetailsServiceImpl(val userRepository: UserRepository): UserDetailsService {

    fun getUserByUsername(username: String): User {
        val userOptional = userRepository.findByUsername(username)
        if (userOptional.isEmpty) throw UsernameNotFoundException("No User with username: $username exists.")
        val user = userOptional.get()
        return user
    }

    override fun loadUserByUsername(username: String): UserDetailsDTO {
        val user = getUserByUsername(username)
        return user.toDTO()
    }

    override fun addUser(customer: Customer, userDTO: UserDetailsDTO) {
        val user = User(
                uid = null,
                username = userDTO.username,
                password = userDTO.password,
                email = userDTO.mail,
                isEnabled = userDTO.isEnabled,
                roles = userDTO.roles,
                customer = customer
        )
        userRepository.save(user)
    }

    override fun addRole(role: Rolename, username: String) {
        val user = getUserByUsername(username)
        user.addRole(role)
    }

    override fun removeRole(role: Rolename, username: String) {
        val user = getUserByUsername(username)
        user.removeRole(role)
    }

    override fun enable(username: String) {
        val user = getUserByUsername(username)
        user.isEnabled = true
    }

    override fun disable(username: String) {
        val user = getUserByUsername(username)
        user.isEnabled = false
    }
}
