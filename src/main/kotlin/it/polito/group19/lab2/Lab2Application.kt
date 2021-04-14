package it.polito.group19.lab2

import it.polito.group19.lab2.domain.Customer
import it.polito.group19.lab2.domain.Wallet
import it.polito.group19.lab2.repositories.CustomerRepository
import it.polito.group19.lab2.repositories.WalletRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Lab2Application{

    @Bean
    fun test(customerRepository: CustomerRepository, walletRepository: WalletRepository): CommandLineRunner {
        return CommandLineRunner{
            val c1= Customer()
            c1.name="giacomo"
            c1.surname="matteotti"
            c1.email = "ciao@matteotti.it"
            c1.deliveryAddress="via dei decollati,23"
            customerRepository.save(c1);
            val c2= Customer()
            c2.name="giacomo"
            c2.surname="matteotti"
            c2.email = "salve@matteotti.it"
            c2.deliveryAddress="via dei decollati,23"
            customerRepository.save(c2);
            customerRepository.findAll().forEach{
                println("Customer(id: ${it.cid},name: ${it.name})");
            }

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
