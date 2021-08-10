package com.markoid.packit.debug.presentation.controllers

import com.markoid.packit.core.presentation.utils.ApiConstants
import com.markoid.packit.debug.data.entities.LogDto
import com.markoid.packit.debug.data.entities.LogEntity
import com.markoid.packit.debug.data.services.DebugServiceImpl
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping(ApiConstants.DEBUG_PATH)
@RestController
class DebugController(private val debugService: DebugServiceImpl) {

    @ApiOperation("Saves a new log into the user provided ")
    @ApiResponse(code = 200, message = "")
    @PostMapping(ApiConstants.SAVE_DEBUG_LOG_URL)
    fun saveLogMessage(@Valid request: LogDto): ResponseEntity<Any> {
        this.debugService.saveLogMessage(request)
        return ResponseEntity.ok(Unit)
    }

    @ApiOperation("Get debug logs for the user provided ")
    @ApiResponse(code = 200, message = "")
    @GetMapping(ApiConstants.GET_LOG_MESSAGES_URL)
    fun getLogsByUserId(
        @RequestParam(required = false) userId: String? = null
    ): ResponseEntity<List<LogEntity>> {
        val result = this.debugService.getLogMessagesByUserId(userId)
        return ResponseEntity.ok(result)
    }

}