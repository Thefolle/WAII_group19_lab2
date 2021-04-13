package it.polito.group19.lab2.repositories

import it.polito.group19.lab2.domain.Wallet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletRepository: CrudRepository<Wallet, Long> {

}