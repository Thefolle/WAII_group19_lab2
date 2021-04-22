package it.polito.group19.lab2.controllers

import it.polito.group19.lab2.domain.Customer
import it.polito.group19.lab2.dto.TransactionDTO
import it.polito.group19.lab2.dto.WalletDTO
import it.polito.group19.lab2.services.WalletServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController
@RequestMapping("/wallets")
class WalletController(private val walletServiceImpl: WalletServiceImpl) {

    @PostMapping("")
    fun addWallet(@RequestBody customer: Customer): ResponseEntity<WalletDTO> {
        val customerId = customer.cid!!
        return ResponseEntity.status(HttpStatus.CREATED).body(
            walletServiceImpl.addWallet(customerId)
        )
    }



    @GetMapping("/{walletId}")
    fun getWallet(@PathVariable("walletId") walletId: Long): ResponseEntity<WalletDTO> {
        return ResponseEntity.status(HttpStatus.OK).body(walletServiceImpl.getWallet(walletId))
    }

    @PostMapping("/{walletId}/transactions")
    fun performTransaction(@PathVariable("walletId") walletId: Long,
                        @RequestBody transaction: TransactionDTO): ResponseEntity<TransactionDTO> {
        return ResponseEntity.status(HttpStatus.OK).body(walletServiceImpl.performTransaction(transaction.creditorId, transaction.debtorId, transaction.transactedMoneyAmount))
    }

    @GetMapping("/{walletId}/transactions/{transactionId}")
    fun getTransaction(@PathVariable("walletId") walletId: Long,
                             @PathVariable("transactionId") transactionId: Long): ResponseEntity<TransactionDTO> {
        return ResponseEntity.status(HttpStatus.OK).body(walletServiceImpl.getTransaction(walletId, transactionId))
    }


    @GetMapping("/{walletId}/transactions")
    fun getTransactions(@PathVariable("walletId") walletId: Long,
                        @RequestParam("from") from: Long,
                        @RequestParam("to") to: Long): ResponseEntity<List<TransactionDTO>> {
        var result = walletServiceImpl.getTransactions(walletId, LocalDateTime.ofEpochSecond(from / 1000, 0, ZoneOffset.UTC), LocalDateTime.ofEpochSecond(to / 1000, 0, ZoneOffset.UTC))

        return if (result.isEmpty())
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        else ResponseEntity.status(HttpStatus.OK).body(result)
    }

}
