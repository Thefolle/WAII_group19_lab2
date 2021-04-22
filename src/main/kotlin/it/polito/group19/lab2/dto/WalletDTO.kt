package it.polito.group19.lab2.dto

data class WalletDTO(
    var wid: Long,
    var balance: Float,
    var customerId: Long,
    var rechargeIds: MutableList<Long>,
    var purchaseIds: MutableList<Long>,
)