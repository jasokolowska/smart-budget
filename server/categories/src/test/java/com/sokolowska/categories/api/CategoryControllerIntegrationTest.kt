package io.smartbudget.categories

import com.sokolowska.categories.api.BaseIntegrationTest
import com.sokolowska.categories.api.CategoryDto
import com.sokolowska.categories.domain.CategoryRepository
import com.sokolowska.categories.infra.CategoryEntity
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class CategoryControllerIntegrationTest : BaseIntegrationTest() {
  @Autowired private lateinit var restTemplate: TestRestTemplate

  @Autowired private lateinit var categoryRepository: CategoryRepository

  @LocalServerPort private var port: Int = 0

  private fun baseUrl() = "http://localhost:$port/api/categories"

  @Test
  fun `should create category successfully`() {
    // given
    val userId = UUID.randomUUID()
    val request =
      mapOf(
        "name" to "Groceries",
        "colorHex" to "#FFCC00",
        "userId" to userId.toString(),
        "description" to "this is description",
      )

    val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
    val entity = HttpEntity(request, headers)

    // when
    val response = restTemplate.postForEntity(baseUrl(), entity, Void::class.java)

    // then
    assertEquals(HttpStatus.CREATED, response.statusCode)
    assertNotNull(response.headers.location)
  }

  @Test
  fun `should return all user categories`() {
    // given
    val userId = UUID.randomUUID()
    val url = "${baseUrl()}?userId=$userId"
    createCategory("Old Name", "#AAAAAA", userId)

    // when
    val response = restTemplate.getForEntity(url, Array<CategoryDto>::class.java)

    // then
    assertEquals(HttpStatus.OK, response.statusCode)
    assertTrue(response.body!!.isEmpty() || response.body!!.all { it.userId == userId })
  }

  @Test
  fun `should update category name`() {
    // given
    val userId = UUID.randomUUID()
    val categoryId = createCategory("Old Name", "#AAAAAA", userId).id
    val updatedRequest = mapOf("name" to "New Name")

    val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
    val entity = HttpEntity(updatedRequest, headers)

    // when
    val response =
      restTemplate.exchange(
        "${baseUrl()}/$categoryId",
        HttpMethod.PUT,
        entity,
        CategoryDto::class.java,
      )

    // then
    assertEquals(HttpStatus.OK, response.statusCode)
    assertEquals("New Name", response.body?.name)
  }

  @Test
  fun `should delete category`() {
    // given
    val userId = UUID.randomUUID()
    val categoryId = createCategory("To Delete", "#DD0000", userId).id

    // when
    val response =
      restTemplate.exchange(
        "${baseUrl()}/$categoryId",
        HttpMethod.DELETE,
        null,
        Void::class.java,
      )

    // then
    assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
  }

  private fun createCategory(
    name: String,
    color: String,
    userId: UUID,
  ): CategoryEntity =
    categoryRepository.save(
      CategoryEntity(
        name = name,
        color = color,
        userId = userId,
      ),
    )
}
