package it.polito.group19.lab2.domain

import it.polito.group19.lab2.dto.CustomerDto
import it.polito.group19.lab2.dto.TransactionDto
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

    fun toDto() = TransactionDto(
        tid=tid,
        transactedMoneyAmount= transactedMoneyAmount,
        timestamp=timestamp,
        creditorId = creditor.wid!!,
        debtorId = debtor.wid!!
    )
}