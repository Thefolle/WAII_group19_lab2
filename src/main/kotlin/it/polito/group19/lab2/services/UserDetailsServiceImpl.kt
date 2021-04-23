package it.polito.group19.lab2.services

import it.polito.group19.lab2.dto.RegisterDTO
import it.polito.group19.lab2.dto.UserDetailsDTO
import it.polito.group19.lab2.domain.Rolename
import it.polito.group19.lab2.domain.User
import it.polito.group19.lab2.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class UserDetailsServiceImpl( val userRepository: UserRepository,
                              val passwordEncoder: PasswordEncoder ): UserDetailsService {

    private fun getUserByUsername(username: String): User {
        val userOptional = userRepository.findByUsername(username)
        if (userOptional.isEmpty) throw UsernameNotFoundException("No User with username: $username exists.")
        val user = userOptional.get()
        return user
    }

    override fun loadUserByUsername(username: String): UserDetailsDTO {
        val user = getUserByUsername(username)
        return user.toDTO()
    }

    override fun addUser( registerDTO: RegisterDTO ) {
        if (userRepository.existsByUsername(registerDTO.username)) {
            throw ResponseStatusException(HttpStatus.IM_USED, "The username is already in use.")
        } else if (userRepository.existsByEmail(registerDTO.email)) {
            throw ResponseStatusException(HttpStatus.IM_USED, "The email is already linked to another account.")
        } else if (registerDTO.password != registerDTO.confirmPassword) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Password and confirmPassword don't match.")
        }

        val user = User(
            uid = null,
            username = registerDTO.username,
            password = passwordEncoder.encode(registerDTO.password),
            email = registerDTO.email,
            isEnabled = false,
            roles = Rolename.CUSTOMER.name
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
