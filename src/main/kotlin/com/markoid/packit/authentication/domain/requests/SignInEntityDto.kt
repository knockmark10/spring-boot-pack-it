package com.markoid.packit.authentication.domain.requests

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

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
    @get:Size(message = "Parameter must be 6 length long.", min = 6)
    var password: String,

    @JsonProperty("fcm_token")
    var firebaseToken: String = ""

)