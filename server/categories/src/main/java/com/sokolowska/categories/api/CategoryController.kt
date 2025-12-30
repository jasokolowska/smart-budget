package com.sokolowska.categories.api

import com.sokolowska.categories.services.CategoryService
import java.util.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("api/categories")
class CategoryController(
  private val categoryService: CategoryService,
) {
  @GetMapping
  fun getCategories(
    @RequestParam(required = false) userId: UUID?,
  ): ResponseEntity<List<CategoryDto>> =
    ResponseEntity.ok(
      categoryService.getAll(userId),
    )

  @PostMapping
  fun addCategory(
    @RequestBody categoryDto: CategoryDto,
  ): ResponseEntity<Void> {
    val categoryId = categoryService.addCategory(categoryDto)
    val resourceUri =
      ServletUriComponentsBuilder.fromCurrentRequestUri()
        .path("/{id}")
        .buildAndExpand(categoryId)
        .toUri()
    return ResponseEntity.created(resourceUri).build()
  }

  @PutMapping("{id}")
  fun updateCategories(
    @PathVariable id: UUID,
    @RequestBody categoryDto: CategoryDto,
  ): ResponseEntity<CategoryDto> =
    ResponseEntity.ok(categoryService.updateCategory(id, categoryDto))

  @DeleteMapping("{id}")
  fun deleteCategory(
    @PathVariable id: UUID,
    @RequestParam(required = true) userId: UUID,
  ): ResponseEntity<Void> {
    categoryService.deleteCategory(id, userId)
    return ResponseEntity.noContent().build()
  }
}
