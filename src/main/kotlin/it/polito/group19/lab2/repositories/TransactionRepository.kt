package it.polito.group19.lab2.repositories

import it.polito.group19.lab2.domain.Transaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TransactionRepository: CrudRepository<Transaction, Long> {
    fun findByTimestampBetween(startDate: LocalDateTime, endDate: LocalDateTime): MutableList<Transaction>
}