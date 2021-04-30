package it.polito.group19.lab2

import it.polito.group19.lab2.domain.*
import it.polito.group19.lab2.repositories.CustomerRepository
import it.polito.group19.lab2.repositories.TransactionRepository
import it.polito.group19.lab2.repositories.UserRepository
import it.polito.group19.lab2.repositories.WalletRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.http.HttpStatus
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@SpringBootApplication
@PropertySource(value = ["classpath:application.properties"], ignoreResourceNotFound = false)
class Lab2Application{

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun test(customerRepository: CustomerRepository,
             walletRepository: WalletRepository,
             transactionRepository: TransactionRepository,
             userRepository: UserRepository
    ): CommandLineRunner {
        return CommandLineRunner{

            // Register users
            val u1 = User(null, "user1", passwordEncoder().encode("pass1"), "user1@gmail.com",
                            false, Rolename.CUSTOMER.toString())
            userRepository.save(u1)

            val u2 = User(null, "user2", passwordEncoder().encode("pass2"), "user2@gmail.com",
                            false, Rolename.CUSTOMER.toString())
            userRepository.save(u2)

            userRepository.findAll().forEach{
                println("User (id: ${it.uid}, uname: ${it.username}, pass: ${it.password}, " +
                        "email: ${it.email}, isEn: ${it.isEnabled}, role: ${it.roles} )")
            }

            // Initialize two customer
            val c1= Customer(null, "giacomo", "matteotti",
                "via dei decollati 23", "gia@email.com", u1)
            customerRepository.save(c1);

            val c2= Customer(null, "sia", "teotti",
                "via Valdieri 19", "siamo@email.com", u2)
            customerRepository.save(c2);

            // Add tree wallet
            val w1 = Wallet(null, 50F, c1)
            walletRepository.save(w1)

            val w2 = Wallet(null, 32F, c1)
            walletRepository.save(w2)

            val  w3 = Wallet(null, 40F, c2)
            walletRepository.save(w3)

            customerRepository.findAll().forEach{
                println("Customer(id: ${it.cid}, name: ${it.name}, surname:${it.surname}, " +
                        "addr: ${it.deliveryAddress}, email: ${it.email}, user: ${it.user.uid} )")
            }

            walletRepository.findAll().forEach{
                println("Wallet: (id: ${it.wid}, balance: ${it.balance}, cid:${it.customer.cid} )")
            }

            // perform transaction
            w1.balance -= 3F;
            w3.balance += 3F;
            walletRepository.save(w1)
            walletRepository.save(w3)
            val t1 = Transaction(null, 3F, LocalDateTime.now(), w1, w3)
            transactionRepository.save(t1)

            transactionRepository.findAll().forEach{
                println("Transaction: (id: ${it.tid}, money: ${it.transactedMoneyAmount}, time: ${it.timestamp}, " +
                        "sender: ${it.creditor.wid}, rec: ${it.debtor.wid} )")
            }

            println("After transaction: ")
            walletRepository.findAll().forEach{
                println("Wallet: (id: ${it.wid}, balance: ${it.balance}, cid:${it.customer.cid} )")
            }

        }
    }

    @Bean
    fun getMailSender(@Value("\${spring.mail.host}") host: String,
                      @Value("\${spring.mail.port}") port: Int,
                      @Value("\${spring.mail.username}") username: String,
                      @Value("\${spring.mail.password}") password: String,
                      @Value("\${spring.mail.protocol}") protocol: String,
                      @Value("\${spring.mail.properties.mail.smtp.auth}") auth: Boolean,
                      @Value("\${spring.mail.properties.mail.smtp.starttls.enable}") enable: Boolean,
                      @Value("\${spring.mail.properties.mail.debug}") debug: Boolean
    ): JavaMailSender {

        var javaMailSenderImpl = JavaMailSenderImpl()
        javaMailSenderImpl.host = host
        javaMailSenderImpl.port = port
        javaMailSenderImpl.username = username
        javaMailSenderImpl.password = password
        val props: Properties = javaMailSenderImpl.javaMailProperties
        props["mail.transport.protocol"] = protocol
        props["mail.smtp.auth"] = auth
        props["mail.smtp.starttls.enable"] = enable
        props["mail.debug"] = debug

        // Uncomment to test the mail connection at startup
        //javaMailSenderImpl.testConnection()

        return javaMailSenderImpl
    }

    @Bean
    fun getAuthenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint() { httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, authenticationException: AuthenticationException ->
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }
    }

}

fun main(args: Array<String>) {
    runApplication<Lab2Application>(*args)
}
