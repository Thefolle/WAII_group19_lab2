package it.polito.group19.lab2.dto

data class CustomerDto(
    var cid: Long,
    var name: String,
    var surname: String,
    var deliveryAddress: String,
    var email: String,
    var walletIds: MutableList<Long>
    )