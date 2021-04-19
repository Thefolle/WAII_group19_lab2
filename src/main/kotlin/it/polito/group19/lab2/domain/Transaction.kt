package it.polito.group19.lab2.domain

import it.polito.group19.lab2.DTO.TransactionDTO
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Min

@Entity
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var tid: Long?,
    @Min(value = 0)
    var transactedMoneyAmount: Float,
    var timestamp: LocalDateTime,
    @ManyToOne(fetch=FetchType.LAZY)
    var creditor: Wallet,
    @ManyToOne(fetch=FetchType.LAZY)
    var debtor: Wallet){

    fun toDto() = TransactionDTO(
        tid=tid,
        transactedMoneyAmount= transactedMoneyAmount,
        timestamp=timestamp,
        creditorId = creditor.wid!!,
        debtorId = debtor.wid!!
    )
}