package it.polito.group19.lab2.services

import it.polito.group19.lab2.domain.Customer
import it.polito.group19.lab2.domain.Transaction
import it.polito.group19.lab2.domain.Wallet
import it.polito.group19.lab2.repositories.CustomerRepository
import it.polito.group19.lab2.repositories.TransactionRepository
import it.polito.group19.lab2.repositories.WalletRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
@Transactional
class WalletServiceImpl(val walletRepository: WalletRepository, val customerRepository: CustomerRepository, val transactionRepository: TransactionRepository): WalletService {

    override fun addWallet(customerId: Long): Wallet {
        var customerOptional = customerRepository.findById(customerId)
        if (customerOptional.isEmpty) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with id ${customerId} exists.")
        var customer = customerOptional.get()
        var wallet = Wallet(null, 0.0F, customer)
        return walletRepository.save(wallet)
    }

    override fun getWallet(walletId: Long): Wallet {
        var walletOptional = walletRepository.findById(walletId)
        if (walletOptional.isEmpty) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No wallet with id ${walletId} exists.")
        return walletOptional.get()
    }

    override fun performTransaction(creditorId: Long, debtorId: Long, transactedMoneyAmount: Float): Transaction {
        var creditor = getWallet(creditorId)
        var debtor = getWallet(debtorId)

        if (debtor.balance < transactedMoneyAmount) throw ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough money!")

        creditor.addBalance(transactedMoneyAmount)
        debtor.addBalance(-transactedMoneyAmount)

        var transaction = Transaction(null, transactedMoneyAmount, LocalDateTime.now(), creditor, debtor)

//      This snippet is useless since the db doesn't store these lists
//        creditor.recharges?.add(transaction)
//        debtor.purchases?.add(transaction)

        return transactionRepository.save(transaction)
    }

    override fun getTransaction(walletId: Long, transactionId: Long): Transaction {

        // This snippet doesn't work because the list of purchases/recharges is not automatically filled by Spring Data
        // return walletRepository.findById(walletId).get().purchases?.find { it.tid?.equals(transactionId) !! }!!

        var transactionOptional = transactionRepository.findById(transactionId)
        if (transactionOptional.isEmpty) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No transaction with id ${transactionId} exists.")
        return transactionOptional.get()
    }

    override fun getTransactions(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<Transaction> {
        if (walletRepository.existsById(walletId) == false) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No wallet with id ${walletId} exists.")

        return transactionRepository
            .findByTimestampBetween(startDate, endDate)
            .filter { it.creditor.wid?.equals(walletId) ?: false || it.debtor.wid?.equals(walletId) ?: false }
    }
}
