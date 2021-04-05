package it.polito.group19.lab2.domain

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Transaction(@Id @GeneratedValue var tid: Long, @ManyToOne(targetEntity = Wallet::class, fetch = FetchType.LAZY) @JoinColumn(referencedColumnName = "wid") var debtor: Wallet, @ManyToOne(targetEntity = Wallet::class, fetch = FetchType.LAZY) @JoinColumn(referencedColumnName = "wid") var creditor: Wallet, @Min(value = 0) var transactedMoneyAmount: Float, var timestamp: LocalDateTime) {
}