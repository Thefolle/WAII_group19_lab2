package it.polito.group19.lab2.repositories

import it.polito.group19.lab2.domain.Transaction
import org.springframework.data.repository.CrudRepository

interface TransactionRepository: CrudRepository<Transaction, Long> {
}