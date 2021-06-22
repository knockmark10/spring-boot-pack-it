package com.markoid.packit.authentication.domain.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class SignInRequest(
    var email: String?,
    var password: String?,
    @JsonProperty("fcm_token")
    var firebaseToken: String = ""
)