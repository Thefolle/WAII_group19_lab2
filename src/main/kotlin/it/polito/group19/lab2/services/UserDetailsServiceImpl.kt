package it.polito.group19.lab2.services

import it.polito.group19.lab2.domain.Customer
import it.polito.group19.lab2.dto.RegisterDTO
import it.polito.group19.lab2.dto.UserDetailsDTO
import it.polito.group19.lab2.domain.Rolename
import it.polito.group19.lab2.domain.User
import it.polito.group19.lab2.domain.Wallet
import it.polito.group19.lab2.dto.LoginDTO
import it.polito.group19.lab2.repositories.CustomerRepository
import it.polito.group19.lab2.repositories.UserRepository
import it.polito.group19.lab2.security.JwtUtils
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional
class UserDetailsServiceImpl(val userRepository: UserRepository,
                             val customerRepository: CustomerRepository,
                             val passwordEncoder: PasswordEncoder,
                             val notificationService: NotificationServiceImpl,
                             val mailService: MailServiceImpl,
                             val jwtUtils: JwtUtils): UserDetailsService {

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

    override fun authenticateUser(loginDTO: LoginDTO): String {
        val user = loadUserByUsername(loginDTO.username)
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)
        val authentication : Authentication = usernamePasswordAuthenticationToken
        return jwtUtils.generateJwtToken(authentication)
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

        //add a customer entity linked to the user one
        val customer = Customer(
                cid = null,
                name = registerDTO.name,
                surname = registerDTO.surname,
                deliveryAddress = registerDTO.address,
                email = registerDTO.email,
                user = user
        )

        customerRepository.save(customer)

        val tokenDTO = notificationService.createToken(user.username)
        mailService.sendMessage(toMail = user.email, subject = "Registration Confirmation",
                mailBody = "Confirm your registration by clicking on the following link: localhost:8080/auth/registrationConfirm?token=" + tokenDTO.token)
    }

    override fun addRole(role: Rolename, username: String) {
        val user = getUserByUsername(username)
        user.addRole(role)
    }

    override fun removeRole(role: Rolename, username: String) {
        val user = getUserByUsername(username)
        user.removeRole(role)
    }

    @Secured("ROLE_ADMIN")
    override fun enable(username: String) {
        val user = getUserByUsername(username)
        user.isEnabled = true
    }

    @Secured("ROLE_ADMIN")
    override fun disable(username: String) {
        val user = getUserByUsername(username)
        user.isEnabled = false
    }

    override fun registrationConfirm(token: String): UserDetailsDTO {
        val verifyToken = notificationService.getToken(token)
        if (!notificationService.tokenNotExpired(verifyToken.token)) throw ResponseStatusException(HttpStatus.NOT_FOUND, "The verification email has expired!")
        enable(verifyToken.username)

        return loadUserByUsername(verifyToken.username)
    }

}
