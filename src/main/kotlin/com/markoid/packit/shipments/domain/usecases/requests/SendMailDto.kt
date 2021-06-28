package com.markoid.packit.shipments.domain.usecases.requests

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.multipart.MultipartFile

data class SendMailDto(

    @JsonProperty("email", required = true)
    val email: String?,

    @JsonProperty("file", required = true)
    val file: MultipartFile? = null

)