package it.polito.group19.lab2.dto

data class WalletDto(
    var wid: Long,
    var balance: Double,
    var customerId: Long,
    var rechargeIds: MutableList<Long>,
    var purchaseIds: MutableList<Long>)