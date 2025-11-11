package com.sokolowska.categories.api

import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  classes = [ModuleIntegrationTestConfig::class],
)
@ActiveProfiles("test")
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseIntegrationTest {
  companion object {
    @Container
    val postgresContainer =
      PostgreSQLContainer<Nothing>("postgres:16").apply {
        withDatabaseName("category_test")
        withUsername("test")
        withPassword("test")
        start()
      }

    @DynamicPropertySource
    fun configureProperties(registry: DynamicPropertyRegistry) {
      registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
      registry.add("spring.datasource.username", postgresContainer::getUsername)
      registry.add("spring.datasource.password", postgresContainer::getPassword)
      registry.add("spring.flyway.enabled") { "true" }
    }
  }
}
