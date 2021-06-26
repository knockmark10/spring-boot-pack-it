package com.markoid.packit.authentication.domain.requests

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

/**
 * Data Transfer Object for Signing In purposes. In-place validations will be performed.
 */
data class SignInEntityDto(

    @JsonProperty("email", required = true)
    @get:NotBlank(message = "Parameter is required.")
    @get:Email(message = "Parameter should be valid.")
    var email: String,

    @JsonProperty("password", required = true)
    @get:NotBlank(message = "Parameter is required.")
    @get:Min(value = 6, message = "Parameter must be 6 length long.")
    var password: String,

    @JsonProperty("fcm_token")
    @get:NotBlank(message = "Parameter is required.")
    var firebaseToken: String = ""

)