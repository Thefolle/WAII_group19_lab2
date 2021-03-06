package it.polito.group19.lab2.services

import it.polito.group19.lab2.domain.Transaction
import it.polito.group19.lab2.domain.Wallet
import java.time.LocalDateTime
interface WalletService {

    fun addWallet(customerId: Long): Wallet

    fun getWallet(walletId: Long): Wallet

    fun performTransaction(creditorId: Long, debtorId: Long, transactedMoneyAmount: Float): Transaction

    /**
     * @param startDate: it is included in the search range
     * @param endDate: it is included in the search range
     */
    fun getTransactions(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<Transaction>

    fun getTransaction(walletId: Long, transactionId: Long): Transaction

}