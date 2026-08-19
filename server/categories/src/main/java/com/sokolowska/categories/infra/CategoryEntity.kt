package com.sokolowska.categories.infra

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "categories")
data class CategoryEntity(
  @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
  @Column(nullable = true) val userId: UUID? = null,
  @Column(nullable = false, length = 100) var name: String = "",
  @Column(length = 20) var color: String? = null,
)
