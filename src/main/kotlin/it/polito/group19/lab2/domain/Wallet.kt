package it.polito.group19.lab2.domain

import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Wallet(@Id @GeneratedValue var wid: Long, @Min(value = 0) var balance: Float, @ManyToOne(fetch=FetchType.LAZY) var customer: Customer, @OneToMany(mappedBy = "creditor") var recharges: MutableList<Transaction>, @OneToMany(mappedBy = "debtor") var purchases: MutableList<Transaction>) {
}