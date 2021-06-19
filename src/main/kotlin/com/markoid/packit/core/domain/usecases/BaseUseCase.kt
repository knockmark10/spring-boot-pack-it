package com.markoid.packit.core.domain.usecases

import org.springframework.http.ResponseEntity

abstract class BaseUseCase<Result, Params> {

    abstract fun execute(request: Params): ResponseEntity<Result>

}