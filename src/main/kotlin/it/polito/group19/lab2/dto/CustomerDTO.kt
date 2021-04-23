package it.polito.group19.lab2.dto

import it.polito.group19.lab2.domain.Wallet
import javax.validation.constraints.Pattern

data class CustomerDTO(
    var cid: Long,
    var name: String,
    var surname: String,
    var deliveryAddress: String,
    @Pattern(regexp = ".*@.*", message = "Invalid email.")
    var email: String,
    var walletIds: MutableList<Wallet>,
    var user: Long

    )