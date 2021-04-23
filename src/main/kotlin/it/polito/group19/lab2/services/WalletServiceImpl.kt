package it.polito.group19.lab2.services

import it.polito.group19.lab2.dto.TransactionDTO
import it.polito.group19.lab2.dto.WalletDTO
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

    private fun getWalletbyId(walletId: Long): Wallet {
        val walletOptional = walletRepository.findById(walletId)
        if (walletOptional.isEmpty) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No wallet with id ${walletId} exists.")
        return walletOptional.get()
    }

    override fun addWallet(customerId: Long): WalletDTO{
        val customerOptional = customerRepository.findById(customerId)
        if (customerOptional.isEmpty) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No customer with id ${customerId} exists.")
        val customer = customerOptional.get()
        val wallet = Wallet(null, 0.0F, customer)
        walletRepository.save(wallet)
        return wallet.toDto()
    }

    override fun getWallet(walletId: Long): WalletDTO {
        val wallet = getWalletbyId(walletId)
        return wallet.toDto()
    }

    override fun performTransaction(creditorId: Long, debtorId: Long, transactedMoneyAmount: Float): TransactionDTO {
        val creditor = getWalletbyId(creditorId)
        val debtor = getWalletbyId(debtorId)

        if (debtor.balance < transactedMoneyAmount) throw ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough money!")

        creditor.addBalance(transactedMoneyAmount)
        debtor.addBalance(-transactedMoneyAmount)

        val transaction = Transaction(null, transactedMoneyAmount, LocalDateTime.now(), creditor, debtor)

//      This snippet is useless since the db doesn't store these lists
//        creditor.recharges?.add(transaction)
//        debtor.purchases?.add(transaction)

        transactionRepository.save(transaction)
        return transaction.toDto()
    }

    override fun getTransaction(walletId: Long, transactionId: Long): TransactionDTO {

        // This snippet doesn't work because the list of purchases/recharges is not automatically filled by Spring Data
        // return walletRepository.findById(walletId).get().purchases?.find { it.tid?.equals(transactionId) !! }!!

        val transactionOptional = transactionRepository.findById(transactionId)
        if (transactionOptional.isEmpty) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No transaction with id ${transactionId} exists.")
        return transactionOptional.get().toDto()
    }

    override fun getTransactions(walletId: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<TransactionDTO> {
        if (walletRepository.existsById(walletId) == false) throw ResponseStatusException(HttpStatus.NOT_FOUND, "No wallet with id ${walletId} exists.")

        return transactionRepository
            .findByTimestampBetween(startDate, endDate).map { it.toDto() }
            .filter { it.creditorId.equals(walletId) ?: false || it.debtorId.equals(walletId) ?: false }
    }
}
