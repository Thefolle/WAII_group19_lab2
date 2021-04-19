package it.polito.group19.lab2

import it.polito.group19.lab2.domain.Rolename
import it.polito.group19.lab2.domain.User
import it.polito.group19.lab2.dto.WalletDto
import it.polito.group19.lab2.repositories.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class Lab2ApplicationTests {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `Test User's role methods`() {
        var user = User(null, "username", "password", "username@password.it")

        assertThat(user.getRoles().size).isEqualTo(0)

        user.addRole(Rolename.ADMIN)
        assertThat(user.getRoles()[0]).isEqualTo(Rolename.ADMIN)

        user.addRole(Rolename.CUSTOMER)
        assertThat(user.getRoles()).contains(Rolename.CUSTOMER)

        user.addRole(Rolename.ADMIN)
        assertThat(user.getRoles().size).isEqualTo(2)

        assertThat(userRepository.save(user).getRoles()).containsAll(user.getRoles())

        user.removeRole(Rolename.ADMIN)
        assertThat(user.getRoles()).doesNotContain(Rolename.ADMIN)

        user.removeRole(Rolename.ADMIN)
        assertThat(user.getRoles()[0]).isEqualTo(Rolename.CUSTOMER)

        user.removeRole(Rolename.CUSTOMER)
        assertThat(user.getRoles().size).isEqualTo(0)

    }

}
