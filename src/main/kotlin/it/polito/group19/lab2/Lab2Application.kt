package it.polito.group19.lab2

import it.polito.group19.lab2.domain.*
import it.polito.group19.lab2.repositories.CustomerRepository
import it.polito.group19.lab2.repositories.TransactionRepository
import it.polito.group19.lab2.repositories.UserRepository
import it.polito.group19.lab2.repositories.WalletRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

@SpringBootApplication
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

//    customerRepository: CustomerRepository, walletRepository: WalletRepository): CommandLineRunner {
//        return CommandLineRunner{
//            val c1= Customer()
//            c1.name="giacomo"
//            c1.surname="matteotti"
//            c1.email = "ciao@matteotti.it"
//            c1.deliveryAddress="via dei decollati,23"
//            customerRepository.save(c1);
//            val c2= Customer()
//            c2.name="giacomo"
//            c2.surname="matteotti"
//            c2.email = "salve@matteotti.it"
//            c2.deliveryAddress="via dei decollati,23"
//            customerRepository.save(c2);
//            customerRepository.findAll().forEach{
//                println("Customer(id: ${it.cid},name: ${it.name})");
//            }

            /*var wallet1 = Wallet(null, 5.0F, c1)
            var wallet2 = Wallet(null, 15.0F, c2)
            walletRepository.save(wallet1)
            walletRepository.save(wallet2)

            walletRepository.findAll().forEach {
                println("Wallet(id: ${it.wid}, balance: ${it.balance}, customerId: ${it.customer.cid}, rechargesCount: ${it.recharges?.size}, purchasesCount: ${it.purchases?.size}")
            }*/

        }
    }
}

fun main(args: Array<String>) {
    runApplication<Lab2Application>(*args)
}
