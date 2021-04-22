package it.polito.group19.lab2.dto

import java.time.LocalDateTime

data class TransactionDTO(
    var tid: Long?,
    var transactedMoneyAmount: Float,
    var timestamp: LocalDateTime?,
    var creditorId: Long,
    var debtorId: Long
    )