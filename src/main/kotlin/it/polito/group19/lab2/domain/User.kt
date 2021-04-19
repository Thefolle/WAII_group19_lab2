package it.polito.group19.lab2.domain

import org.hibernate.annotations.ColumnDefault
import javax.persistence.*
import javax.validation.constraints.Pattern

@Entity
@Table(indexes = [Index(name = "usernameIndex", columnList = "username", unique = true)])
class User(
    @Id @GeneratedValue var uid: Long,
    @Column(unique = true, nullable = false) @Pattern(regexp = "^.{8,}$") var username: String,
    var password: String,
    @Column(unique = true, nullable = false) @Pattern(regexp = ".*@.*") var email: String,
    @ColumnDefault("false") var isEnabled: Boolean = false,
    var roles: String = "",
    @OneToOne var customer: Customer
    ) {

    fun getRoles(): List<Rolename> {
        return roles.split("_").map { Rolename.valueOf(it) }
    }

    fun addRole(role: Rolename) {
        if (!roles.contains(role.name))
            roles = roles.plus("_").plus(role)
    }

    fun removeRole(role: Rolename) {
        if (roles.contains(role.name)) {
            roles = roles.split("_").filter { !it.equals(role) }.joinToString("_")
        }
    }

}