package it.polito.group19.lab2.DTO

import it.polito.group19.lab2.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsDTO(
          var uid: Long?,
          var uname: String,
          var mail: String,
          var pass: String,
          var isEn:Boolean,
          var roles:String): UserDetails {


    /**
     * Returns the password used to authenticate the user.
     * @return the password
     */
    override fun getPassword(): String {
        return pass
    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * `null`.
     * @return the username (never `null`)
     */
    override fun getUsername(): String {
        return uname
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     * @return `true` if the user is enabled, `false` otherwise
     */
    override fun isEnabled(): Boolean {
        return isEn
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }
}
