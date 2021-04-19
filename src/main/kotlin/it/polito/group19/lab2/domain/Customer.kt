package it.polito.group19.lab2.domain

import it.polito.group19.lab2.DTO.CustomerDTO
import javax.persistence.*

@Entity
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var cid: Long?=0,
    var name: String?=" ",
    var surname: String?=" ",
    var deliveryAddress: String?=" ",
    @Column(unique = true) var email: String? = " ",
    @OneToMany(targetEntity = Wallet::class, mappedBy = "wid")
    var wallets: MutableList<Wallet>?= mutableListOf<Wallet>()) {

    fun toDTO() = CustomerDTO(
        cid = cid!!,
        name = "$name",
        surname = "$surname",
        deliveryAddress = "$deliveryAddress",
        email = "$email",
        walletIds=wallets?: mutableListOf<Wallet>())
}
