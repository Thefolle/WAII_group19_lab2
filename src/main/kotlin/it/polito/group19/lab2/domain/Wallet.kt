package it.polito.group19.lab2.domain

import it.polito.group19.lab2.dto.WalletDto
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Wallet(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var wid: Long?=0,
    @Min(value = 0)
    var balance: Float = 0.0F,
    @ManyToOne(fetch=FetchType.LAZY)
    var customer: Customer,
    @OneToMany(mappedBy = "creditor")
    var recharges: MutableList<Transaction>?= mutableListOf< Transaction>(),
    @OneToMany(mappedBy = "debtor")
    var purchases: MutableList<Transaction>?= mutableListOf< Transaction>()) {

    fun toDto() = WalletDto(
        wid = wid !!,
        balance = balance,
        customerId = customer.cid!!,
        rechargeIds = recharges?.filter { it.transactedMoneyAmount > 0 }?.map { it.tid } as MutableList<Long>,
        purchaseIds = purchases?.filter { it.transactedMoneyAmount < 0 }?.map { it.tid } as MutableList<Long>

    )

    fun addBalance(difference: Float): Float {
        if (balance + difference  < 0.0F) {
            balance = 0.0F
        } else {
            balance += difference
        }

        return balance
    }


}