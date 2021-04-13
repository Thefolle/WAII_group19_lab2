package it.polito.group19.lab2.controllers

import it.polito.group19.lab2.domain.Customer
import it.polito.group19.lab2.dto.WalletDto
import it.polito.group19.lab2.services.WalletServiceImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wallet")
class WalletController(private  val walletServiceImpl: WalletServiceImpl) {

    @PostMapping("/wallet")
    fun addWallet(@RequestBody c: Customer) {

    }


    @GetMapping("/wallet/{walletId}")
    fun getWallet(@PathVariable("walletId") walletId: Long): WalletDto {
        return walletServiceImpl.getWallet(walletId).toDto()
    }

//    @PostMapping("/wallet/{walletId}/transaction")
//    fun performTransaction(@PathVariable("walletId") walletId: Long,
//                        @RequestBody t: TransactionDto): TransactionDto{
//        return
//    }
//
//    @GetMapping("/wallet/{walletId}/transactions?from=<dateInMillis>&to=<dateInMillis>")
//    fun getTransactions(@PathVariable("walletId") walletId: Long,
//                                   @RequestParam("from") from: Long,
//                                   @RequestParam("to") to: Long): List<TransactionDto>{
//        return
//    }
//
//    @GetMapping("/wallet/{walletId}/transactions/{transactionId}")
//    fun getSingleTransaction(@PathVariable("waletId") walletId: Long,
//                             @PathVariable("transactionId") transactionId: Long): TransactionDto{
//        return
//    }

}