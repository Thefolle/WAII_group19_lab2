package it.polito.group19.lab2

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import it.polito.group19.lab2.domain.Rolename
import it.polito.group19.lab2.dto.RegisterDTO
import it.polito.group19.lab2.repositories.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTests {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var mvc: MockMvc

    companion object {
        private lateinit var objectMapper: ObjectMapper

        @BeforeAll
        @JvmStatic
        fun initializeTestEnvironment() {
            objectMapper = ObjectMapper()
        }
    }


    @Test
    fun `Test request user registration`() {
        // Try to register with mismatching password and confirmPassword
        var registerDTOMismatchingPasswords = RegisterDTO(
            "username1", "username@mail.com", "Dalmau", "Sala",
            "5th Avenue", "myPassword", "anotherPassword"
        )

        registerMismatchingPasswords(registerDTOMismatchingPasswords)


        // Register in a normal case
        var registerDTO = RegisterDTO(
            "username1", "username@mail.com", "Dalmau", "Sala",
            "5th Avenue", "myPassword", "myPassword"
        )

        register(registerDTO)

        var retrievedUser = userRepository.findByUsername(registerDTO.username).get()
        Assertions.assertThat(retrievedUser.email).isEqualTo(registerDTO.email)
        Assertions.assertThat(retrievedUser.isEnabled).isFalse
        Assertions.assertThat(retrievedUser.getRoles()[0]).isEqualTo(Rolename.CUSTOMER)

    }

    private fun registerMismatchingPasswords(registerDTO: RegisterDTO) {
        var body = objectMapper.writeValueAsString(registerDTO);

        var rawResponse = mvc?.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(body))?.andExpect(status().isConflict);

    }

    private fun register(registerDTO: RegisterDTO) {
        var body = objectMapper.writeValueAsString(registerDTO);

        var rawResponse = mvc?.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(body))?.andExpect(status().isCreated);

    }

}