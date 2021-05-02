package it.polito.group19.lab2.domain

enum class Rolename {
    CUSTOMER,
    ADMIN;

    override fun toString(): String {
        return "ROLE_${this.name}"
    }
}