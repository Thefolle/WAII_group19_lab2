package it.polito.group19.lab2.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class RegisterDTO(
    @get: Size(min = 5, max = 25)
    val username: String,
    @get: Pattern(regexp = ".*@.*", message = "Invalid email.")
    val email: String,
//    @NotBlank(message = "The name cannot be empty.")
    @get :NotBlank(message = "The name cannot be empty.")
    val name: String,
//    @NotBlank(message = "The surname cannot be empty.")
    @get :NotBlank(message = "The surname cannot be empty.")
    val surname: String,
    val address: String,
    @get :Size(min = 6, message = "The password length has to be greater or equal than 6.")
    val password: String,
    val confirmPassword: String
)