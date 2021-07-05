package com.markoid.packit.tracking.presentation.controllers

import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.tracking.data.entities.HistoryEntity
import com.markoid.packit.tracking.domain.usecases.GetHistoryByUserIdUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

// FIXME: Should be moved to tracking controller
@RequestMapping(ApiConstants.HISTORY_PATH)
@RestController
class HistoryController(
    private val getHistoryByUserIdUseCase: GetHistoryByUserIdUseCase
) {

    @GetMapping
    fun getHistoryByUserId(
        @RequestParam(ApiConstants.PARAM_USER_ID_UPPER_CASE) userId: String?
    ): ResponseEntity<List<HistoryEntity>> {
        val result = this.getHistoryByUserIdUseCase.startCommand(userId)
        return ResponseEntity.ok(result)
    }

}