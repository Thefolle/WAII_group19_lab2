package it.polito.group19.lab2.dto

import java.time.LocalDateTime

data class TransactionDto(
    var tid: Long,
    var transactedMoneyAmount: Float,
    var timestamp: LocalDateTime,
    var creditorId: Long,
    var debtorId: Long
    )