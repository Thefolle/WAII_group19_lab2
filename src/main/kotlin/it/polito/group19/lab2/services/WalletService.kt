package it.polito.group19.lab2.services

import it.polito.group19.lab2.dto.TransactionDTO
import it.polito.group19.lab2.dto.WalletDTO
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
