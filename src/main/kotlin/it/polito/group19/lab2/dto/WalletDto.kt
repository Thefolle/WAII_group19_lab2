package it.polito.group19.lab2.dto

import it.polito.group19.lab2.domain.Customer

data class WalletDto(
    var wid: Long,
    var balance: Float,
    var customerId: Long,
    var rechargeIds: MutableList<Long>,
    var purchaseIds: MutableList<Long>,
)