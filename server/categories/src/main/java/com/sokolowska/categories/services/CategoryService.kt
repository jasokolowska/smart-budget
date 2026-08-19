package com.sokolowska.categories.services

import com.sokolowska.categories.api.CategoryDto
import com.sokolowska.categories.domain.CategoryRepository
import com.sokolowska.categories.infra.CategoryEntity
import jakarta.persistence.EntityNotFoundException
import java.util.*
import org.springframework.stereotype.Service

@Service
class CategoryService(
  private val categoryRepository: CategoryRepository,
) {
  fun addCategory(categoryDto: CategoryDto): UUID? {
    val entity =
      CategoryEntity(
        userId = categoryDto.userId,
        name = categoryDto.name,
        color = categoryDto.colorHex,
      )
    return categoryRepository.save(entity).id
  }

  fun getAll(userId: UUID?): List<CategoryDto> =
    userId?.let { categoryRepository.findByUserId(it).map { c -> c.toDto() } }
      ?: categoryRepository.findAll().map { it.toDto() }

  fun CategoryEntity.toDto() =
    CategoryDto(
      this.id,
      this.name,
      "description is empty",
      this.color,
      this.userId,
    )

  fun updateCategory(
    id: UUID,
    categoryDto: CategoryDto,
  ): CategoryDto {
    val category =
      categoryRepository.findById(id).orElseThrow {
        EntityNotFoundException("Category with id $id not found")
      }
    category.name = categoryDto.name
    category.color = categoryDto.colorHex

    return categoryRepository.save(category).toDto()
  }

  fun deleteCategory(
    id: UUID,
    userId: UUID,
  ) =
    categoryRepository.findByIdAndUserId(id, userId)?.let { categoryRepository.delete(it) }
      ?: throw EntityNotFoundException(
        "Category with id" + " $id not found",
      )
}
