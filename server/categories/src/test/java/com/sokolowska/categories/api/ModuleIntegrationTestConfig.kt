package com.sokolowska.categories.api

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Test configuration for Category module integration tests. This configuration enables Spring Boot
 * autoconfiguration without requiring the main application class from the app module.
 *
 * Key features:
 * - Enables web MVC for REST controller testing via @EnableAutoConfiguration
 * - Enables JPA and Hibernate for repository testing via @EnableAutoConfiguration
 * - Scans only the categories package to avoid conflicts with other modules
 * - TestRestTemplate is automatically configured when using @SpringBootTest with RANDOM_PORT
 *
 * Usage:
 * - Use this class in @SpringBootTest(classes = [ModuleIntegrationTestConfig::class])
 * - The @EnableAutoConfiguration annotation will automatically configure:
 *     - DataSource (from testcontainers or test properties)
 *     - JPA/Hibernate
 *     - Web MVC (for REST controllers)
 *     - TestRestTemplate (when webEnvironment = RANDOM_PORT is used)
 *     - All other Spring Boot autoconfigurations based on classpath
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = ["com.sokolowska.categories"])
@EnableJpaRepositories(basePackages = ["com.sokolowska.categories"])
@EntityScan(basePackages = ["com.sokolowska.categories"])
internal open class ModuleIntegrationTestConfig
