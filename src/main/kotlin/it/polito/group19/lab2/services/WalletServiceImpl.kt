package it.polito.group19.lab2.services

import it.polito.group19.lab2.domain.Transaction
import it.polito.group19.lab2.domain.Wallet
import it.polito.group19.lab2.repositories.CustomerRepository
import it.polito.group19.lab2.repositories.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class WalletServiceImpl(val walletRepository: WalletRepository, val customerRepository: CustomerRepository): WalletService {

    override fun addWallet(customerId: Long): Boolean{

        var customer= customerRepository.findById(customerId).get()
        var wallet: Wallet
        wallet=
        TODO("Not yet implemented")
    }

    override fun getWallet(walletId: Long): Wallet {
     return   walletRepository.findById(walletId).get()
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