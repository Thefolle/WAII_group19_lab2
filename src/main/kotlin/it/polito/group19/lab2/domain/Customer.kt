package it.polito.group19.lab2.domain

import it.polito.group19.lab2.dto.CustomerDto
import javax.persistence.*

@Entity
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var cid: Long?=0,
    var name: String?=" ",
    var surname: String?=" ",
    var deliveryAddress: String?=" ",
    @Column(unique = true) var email: String?=" ",
    @OneToMany(targetEntity = Wallet::class, mappedBy = "wid") var wallets: MutableList<Wallet>?= mutableListOf<Wallet>()) {
    fun toDTO() = CustomerDto(
        cid = cid ?: 0,
        name = "$name",
        surname = "$surname",
        deliveryAddress = "$deliveryAddress",
        email = "$email",
        walletIds=wallets?: mutableListOf<Wallet>())
}
