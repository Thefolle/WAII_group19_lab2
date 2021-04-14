package it.polito.group19.lab2

import it.polito.group19.lab2.domain.Customer
import it.polito.group19.lab2.repositories.CustomerRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Lab2Application{

    @Bean
    fun test(customerRpository: CustomerRepository): CommandLineRunner {
        return CommandLineRunner{
            val c1= Customer()
            c1.name="giacomo"
            c1.surname="matteotti"
            c1.email = "ciao@matteotti.it"
            c1.deliveryAddress="via dei decollati,23"
            customerRpository.save(c1);
            val c2= Customer()
            c2.name="giacomo"
            c2.surname="matteotti"
            c2.email = "salve@matteotti.it"
            c2.deliveryAddress="via dei decollati,23"
            customerRpository.save(c2);
            customerRpository.findAll().forEach{
                println("Customer(id: ${it.cid},name: ${it.name})");
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Lab2Application>(*args)
}
