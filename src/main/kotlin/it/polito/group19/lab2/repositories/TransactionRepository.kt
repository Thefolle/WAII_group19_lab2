package it.polito.group19.lab2.repositories

import it.polito.group19.lab2.domain.Transaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository: CrudRepository<Transaction, Long> {
}