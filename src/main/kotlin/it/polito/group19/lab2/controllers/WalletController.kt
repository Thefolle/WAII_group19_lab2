package it.polito.group19.lab2.controllers

import it.polito.group19.lab2.domain.Customer
import it.polito.group19.lab2.domain.Wallet
import it.polito.group19.lab2.dto.TransactionDto
import it.polito.group19.lab2.dto.WalletDto
import it.polito.group19.lab2.services.WalletServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController
@RequestMapping("/wallet")
class WalletController(private val walletServiceImpl: WalletServiceImpl) {

    @PostMapping("")
    fun addWallet(@RequestBody customer: Customer): ResponseEntity<WalletDto> {
        val customerId = customer.cid!!
        return ResponseEntity.status(HttpStatus.CREATED).body(
            walletServiceImpl.addWallet(customerId).toDto()
        )
    }


    @GetMapping("/{walletId}")
    fun getWallet(@PathVariable("walletId") walletId: Long): ResponseEntity<WalletDto> {
        return ResponseEntity.status(HttpStatus.OK).body(walletServiceImpl.getWallet(walletId).toDto())
    }

    @PostMapping("/{walletId}/transaction")
    fun performTransaction(@PathVariable("walletId") walletId: Long,
                        @RequestBody transaction: TransactionDto): ResponseEntity<TransactionDto> {
        return ResponseEntity.status(HttpStatus.OK).body(walletServiceImpl.performTransaction(transaction.creditorId, transaction.debtorId, transaction.transactedMoneyAmount).toDto())
    }

    @GetMapping("/{walletId}/transactions/{transactionId}")
    fun getTransaction(@PathVariable("walletId") walletId: Long,
                             @PathVariable("transactionId") transactionId: Long): ResponseEntity<TransactionDto> {
        return ResponseEntity.status(HttpStatus.OK).body(walletServiceImpl.getTransaction(walletId, transactionId).toDto())
    }


    @GetMapping("/{walletId}/transactions")
    fun getTransactions(@PathVariable("walletId") walletId: Long,
                        @RequestParam("from") from: Long,
                        @RequestParam("to") to: Long): ResponseEntity<List<TransactionDto>> {
        var result = walletServiceImpl.getTransactions(walletId, LocalDateTime.ofEpochSecond(from / 1000, 0, ZoneOffset.UTC), LocalDateTime.ofEpochSecond(to / 1000, 0, ZoneOffset.UTC))

        return if (result.isEmpty())
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        else ResponseEntity.status(HttpStatus.OK).body(result.map { it.toDto() })
    }

}
