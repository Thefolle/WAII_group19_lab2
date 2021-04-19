package it.polito.group19.lab2.DTO

import it.polito.group19.lab2.domain.Wallet

data class CustomerDTO(
    var cid: Long,
    var name: String,
    var surname: String,
    var deliveryAddress: String,
    var email: String,
    var walletIds: MutableList<Wallet>

    )