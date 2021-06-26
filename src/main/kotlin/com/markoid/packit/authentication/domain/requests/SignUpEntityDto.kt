package com.markoid.packit.authentication.domain.requests

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.*


/**
 * Data Transfer Object for Sign Up purposes. In-place validations will be performed
 */
data class SignUpEntityDto(

    @JsonProperty(value = "name", required = true)
    @get:NotBlank(message = "Parameter is required.")
    @get:NotEmpty(message = "Parameter should not be empty.")
    @get:Min(message = "Parameter should be at least 4 length long.", value = 4)
    val name: String,

    @JsonProperty(value = "lastName", required = true)
    @get:NotBlank(message = "Parameter is required.")
    @get:NotEmpty(message = "Parameter should not be empty.")
    @get:Size(message = "Parameter should be at least 4 length long.", min = 4)
    val lastName: String,

    @JsonProperty(value = "email", required = true)
    @get:NotBlank(message = "Parameter is required.")
    @get:NotEmpty(message = "Parameter should not be empty.")
    @get:Email(message = "Parameter should be valid")
    val email: String,

    @JsonProperty(value = "password", required = true)
    @get:NotBlank(message = "Parameter is required.")
    @get:NotEmpty(message = "Parameter should not be empty.")
    @get:Size(message = "Parameter should be at least 6 length long.", min = 6)
    val password: String,

    @JsonProperty(value = "userType", required = true)
    val userType: UserType

)