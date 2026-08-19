package com.sokolowska.categories.api

import java.util.*

data class CategoryDto(
  val id: UUID?,
  val name: String,
  val description: String?,
  val colorHex: String?,
  val userId: UUID?,
)
