package it.polito.group19.lab2.services

import it.polito.group19.lab2.domain.Transaction
import it.polito.group19.lab2.domain.Wallet
import it.polito.group19.lab2.repositories.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class WalletServiceImpl(val walletRepository: WalletRepository): WalletService {

    override fun addWallet(customerId: Long): Wallet {
        TODO("Not yet implemented")
    }

    override fun getWallet(walletId: Long): Wallet {
        TODO("Not yet implemented")
    }

    override fun performTransaction(creditorId: Long, debtorId: Long, transactedMoneyAmount: Float) {
        TODO("Not yet implemented")
    }

    override fun getTransactions(walletId: Long): List<Transaction> {
        TODO("Not yet implemented")
    }

    override fun getTransactions(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<Transaction> {
        TODO("Not yet implemented")
    }
}