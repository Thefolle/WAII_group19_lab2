package it.polito.group19.lab2.domain

import javax.persistence.*

@Entity
class Customer(@Id @GeneratedValue var cid: Long, var name: String, var surname: String, var deliveryAddress: String, @Column(unique = true) var email: String, @OneToMany(targetEntity = Wallet::class, mappedBy = "wid") var wallets: MutableList<Wallet>) {

}