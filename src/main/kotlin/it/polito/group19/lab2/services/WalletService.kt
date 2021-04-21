package it.polito.group19.lab2.services

import it.polito.group19.lab2.DTO.TransactionDTO
import it.polito.group19.lab2.DTO.WalletDTO
import it.polito.group19.lab2.domain.Transaction
import it.polito.group19.lab2.domain.Wallet
import java.time.LocalDateTime
interface WalletService {

    fun addWallet(customerId: Long): WalletDTO

    fun getWallet(walletId: Long): WalletDTO

    fun performTransaction(creditorId: Long, debtorId: Long, transactedMoneyAmount: Float): TransactionDTO

    /**
     * @param startDate: it is included in the search range
     * @param endDate: it is included in the search range
     */
    fun getTransactions(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<TransactionDTO>

    fun getTransaction(walletId: Long, transactionId: Long): TransactionDTO

}
