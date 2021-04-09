package it.polito.group19.lab2.repositories

import it.polito.group19.lab2.domain.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepository: CrudRepository<Customer, Long> {
}