package com.sokolowska.categories.api

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionsHandler {
  @ExceptionHandler(EntityNotFoundException::class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  fun handleResourceNotFound(ex: EntityNotFoundException): Map<String, String> =
    mapOf("msg" to (ex.localizedMessage ?: ex.message ?: "Resource not found"))
}
