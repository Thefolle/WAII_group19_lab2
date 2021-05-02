package it.polito.group19.lab2

import io.jsonwebtoken.lang.Assert
import it.polito.group19.lab2.domain.Rolename
import it.polito.group19.lab2.dto.UserDetailsDTO
import it.polito.group19.lab2.security.JwtUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtTests {

    @Test
    fun `Test that generated token is valid`(){
        val jwt = "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE2MTk0NTAwNDksImlzcyI6Im51bGwiLCJleHAiOjE2MTk0NTE4NDksInVzZXJuYW1lIjoidXNlcjUiLCJyb2xlcyI6IkNVU1RPTUVSIn0.6t1m0i8lxEktp7IAJiSMauWL7BO46JwSzQkyzOLv2nnP5YId6ci_A6jgTU4A2ge3"

        val valid = JwtUtils().validateJwtToken(jwt)

        assert(valid)
    }

    @Test
    fun `Test that getting details from JwtToken correctly`(){
        val jwt = "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE2MTk0NTI2MDQsImlzcyI6Im51bGwiLCJleHAiOjE2MTk0NTQ0MDQsInVzZXJuYW1lIjoidXNlcjUiLCJyb2xlcyI6IkNVU1RPTUVSIn0.KwTvvQkrEExJaFsnqukdCHEZRrCclNPB-N8rQ5_VcNXafwNw_Z8Zd-Q9wnxSOlOm"
        val details = JwtUtils().getDetailsFromJwtToken(jwt)

        println("user: ${details.uname}, ${details.roles}")

        assert(details.uname == "user5" && details.roles == Rolename.CUSTOMER.toString())
    }
}