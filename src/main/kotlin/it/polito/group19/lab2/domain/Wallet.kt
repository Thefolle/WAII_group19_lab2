package it.polito.group19.lab2.domain

import it.polito.group19.lab2.dto.WalletDto
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Wallet(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var wid: Long?=0,
    @Min(value = 0) var balance: Double =0.0,
    @ManyToOne(fetch=FetchType.LAZY) var customer: Customer?=Customer(),
    @OneToMany(mappedBy = "creditor") var recharges: MutableList<Transaction>?= mutableListOf< Transaction>(),
    @OneToMany(mappedBy = "debtor") var purchases: MutableList<Transaction>)
    fun Wallet.toDto()= WalletDto(
        wid=wid?:0,
        balance = balance,
        customerId = customer?.cid?:0,
        rechargeIds = recharges?.filter{it.transactedMoneyAmount>0}?.map{it.tid} as MutableList<Long>,
        purchaseIds = purchases?.filter { it.transactedMoneyAmount<0 }?.map{it.tid} as MutableList<Long>,
        
    )
)