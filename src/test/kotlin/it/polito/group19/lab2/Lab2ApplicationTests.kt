package it.polito.group19.lab2

import it.polito.group19.lab2.dto.WalletDto
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest
abstract class Lab2ApplicationTests {

//    @Autowired
//    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun contextLoads() {
//        println("Test result: " + testRestTemplate.postForObject("http://localhost:8080/wallets", 1, WalletDto::class.java).wid)
//        assertTrue(false)
    }

}
