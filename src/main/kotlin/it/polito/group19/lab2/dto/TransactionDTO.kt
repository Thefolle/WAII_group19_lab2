package it.polito.group19.lab2.dto

import java.time.LocalDateTime
import javax.validation.constraints.Min

data class TransactionDTO(
    var tid: Long?,
    @Min(value = 0, message = "The transacted money amount must be non-negative.")
    var transactedMoneyAmount: Float,
    var timestamp: LocalDateTime?,
    var creditorId: Long,
    var debtorId: Long
    )