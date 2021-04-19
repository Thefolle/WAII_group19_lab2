package it.polito.group19.lab2.DTO

import it.polito.group19.lab2.domain.User
import org.springframework.security.core.userdetails.UserDetails

 abstract class UserDetailsDTO: UserDetails

  {
      constructor(
          uid: Long?,
          uname: String,
          mail: String,
          pass:String,
          isEn:Boolean,
          roles:String
      )


    /**
     * Returns the password used to authenticate the user.
     * @return the password
     */
    override fun getPassword(): String {
        TODO("Not yet implemented")
    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * `null`.
     * @return the username (never `null`)
     */
    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     * @return `true` if the user is enabled, `false` otherwise
     */
    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }
}