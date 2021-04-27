package it.polito.group19.lab2.domain

import it.polito.group19.lab2.dto.CustomerDTO
import javax.persistence.*

@Entity
class Customer(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var cid: Long?,
        var name: String?,
        var surname: String?,
        var deliveryAddress: String?,
        @Column(unique = true)
        var email: String?,
        @OneToOne
        var user: User,
        @OneToMany(targetEntity = Wallet::class, mappedBy = "wid")
        var wallets: MutableList<Wallet>?= mutableListOf<Wallet>()
    ) {

    fun toDTO() = CustomerDTO(
        cid = cid!!,
        name = this.name ?: "",
        surname = this.surname ?: "",
        deliveryAddress = deliveryAddress ?: "",
        email = email ?: "",
        walletIds=wallets?: mutableListOf<Wallet>(),
        user = user.uid!! )
}
