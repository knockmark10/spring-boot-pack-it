package com.markoid.packit.tracking.presentation.controllers

import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.domain.usecases.GetHistoryByUserIdUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// FIXME: Should be moved to tracking controller
@RequestMapping(ApiConstants.HISTORY_PATH)
@RestController
class HistoryController(
    private val getHistoryByUserIdUseCase: GetHistoryByUserIdUseCase
) {

    @GetMapping
    fun getHistoryByUserId(
        @RequestHeader(ApiConstants.HEADER_LANGUAGE, required = false) language: String = "en",
        @RequestParam("userId") userId: String?
    ): ResponseEntity<List<HistoryEntity>> = this.getHistoryByUserIdUseCase.setLanguage(language).startCommand(userId)

}