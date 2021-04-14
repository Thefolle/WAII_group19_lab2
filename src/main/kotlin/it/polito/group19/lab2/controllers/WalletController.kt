package it.polito.group19.lab2.controllers

import it.polito.group19.lab2.domain.Customer
import it.polito.group19.lab2.domain.Wallet
import it.polito.group19.lab2.dto.TransactionDto
import it.polito.group19.lab2.dto.WalletDto
import it.polito.group19.lab2.services.WalletServiceImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wallets", consumes = ["application/json"], produces = ["application/json"])
class WalletController(private val walletServiceImpl: WalletServiceImpl) {

    @PostMapping("")
    fun addWallet(@RequestBody customerId: Long): WalletDto {
        return walletServiceImpl.addWallet(customerId).toDto()
    }


    @GetMapping("/{walletId}")
    fun getWallet(@PathVariable("walletId") walletId: Long): WalletDto {
        return walletServiceImpl.getWallet(walletId).toDto()
    }

    @PostMapping("/{walletId}/transactions")
    fun performTransaction(@PathVariable("walletId") walletId: Long,
                        @RequestBody transaction: TransactionDto): TransactionDto{
        return walletServiceImpl.performTransaction(transaction.creditorId, transaction.debtorId, transaction.transactedMoneyAmount).toDto()
    }
//
//    @GetMapping("/{walletId}/transactions?from=<dateInMillis>&to=<dateInMillis>")
//    fun getTransactions(@PathVariable("walletId") walletId: Long,
//                                   @RequestParam("from") from: Long,
//                                   @RequestParam("to") to: Long): List<TransactionDto>{
//        return
//    }

    @GetMapping("/{walletId}/transactions/{transactionId}")
    fun getTransaction(@PathVariable("walletId") walletId: Long,
                             @PathVariable("transactionId") transactionId: Long): TransactionDto{
        return walletServiceImpl.getTransaction(walletId, transactionId).toDto()
    }

}