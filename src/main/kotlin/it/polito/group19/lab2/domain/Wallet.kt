package it.polito.group19.lab2.domain

import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Wallet(@Id @GeneratedValue var wid: Long, @Min(value = 0) var balance: Float, @ManyToOne(targetEntity = Customer::class, fetch=FetchType.LAZY) @JoinColumn(referencedColumnName = "cid") var customer: Customer, @OneToMany(targetEntity = Transaction::class, mappedBy = "tid") var recharges: MutableList<Transaction>, @OneToMany(targetEntity = Transaction::class, mappedBy = "tid") var purchases: MutableList<Transaction>) {
}