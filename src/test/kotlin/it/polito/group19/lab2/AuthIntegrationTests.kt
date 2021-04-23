package it.polito.group19.lab2

import it.polito.group19.lab2.services.MailServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthIntegrationTests {

    @Autowired
    lateinit var mailService: MailServiceImpl

    @Test
    fun `Test that the mail is actually sent`() {
        mailService.sendMessage("cdavide8@gmail.com", "WAII project test", "Here is a test body.")
    }

}