package it.polito.group19.lab2.services

import it.polito.group19.lab2.domain.Transaction
import it.polito.group19.lab2.domain.Wallet
import it.polito.group19.lab2.repositories.CustomerRepository
import it.polito.group19.lab2.repositories.TransactionRepository
import it.polito.group19.lab2.repositories.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class WalletServiceImpl(val walletRepository: WalletRepository, val customerRepository: CustomerRepository, val transactionRepository: TransactionRepository): WalletService {

    override fun addWallet(customerId: Long): Wallet {
        var customer = customerRepository.findById(customerId).get()
        var wallet : Wallet = Wallet(null, 0.0F, customer)
        return walletRepository.save(wallet)
    }

    override fun getWallet(walletId: Long): Wallet {
        return walletRepository.findById(walletId).get()
    }

    override fun performTransaction(creditorId: Long, debtorId: Long, transactedMoneyAmount: Float): Transaction {
        var creditor = walletRepository.findById(creditorId).get()
        var debtor = walletRepository.findById(debtorId).get()

        creditor.addBalance(transactedMoneyAmount)
        debtor.addBalance(-transactedMoneyAmount)

        var transaction = Transaction(null, transactedMoneyAmount, LocalDateTime.now(), creditor, debtor)
        creditor.recharges?.add(transaction)
        debtor.purchases?.add(transaction)
        transactionRepository.save(transaction)

        return transaction
    }

    override fun getTransaction(walletId: Long, transactionId: Long): Transaction {
        // This snippet doesn't work because the list of purchases/recharges is not automatically filled by Spring Data
        // return walletRepository.findById(walletId).get().purchases?.find { it.tid?.equals(transactionId) !! }!!

        return transactionRepository.findById(transactionId).get()
    }

    override fun getTransactions(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<Transaction> {
        return transactionRepository
            .findByTimestampBetween(startDate, endDate)
            .filter { it.creditor.wid?.equals(walletId) ?: false || it.debtor.wid?.equals(walletId) ?: false }
    }
}