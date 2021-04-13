package it.polito.group19.lab2.repositories

import it.polito.group19.lab2.domain.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: CrudRepository<Customer, Long> {
}