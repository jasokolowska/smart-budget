package com.sokolowska.categories.domain

import com.sokolowska.categories.infra.CategoryEntity
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<CategoryEntity, UUID> {
  fun findByUserId(userId: UUID): List<CategoryEntity>
}
