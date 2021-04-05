package it.polito.group19.lab2.domain

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Transaction(@Id @GeneratedValue var tid: Long, @Min(value = 0) var transactedMoneyAmount: Float, var timestamp: LocalDateTime, @ManyToOne var creditor: Wallet, @ManyToOne var debtor: Wallet) {
}