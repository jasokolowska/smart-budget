package com.sokolowska.categories.api

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = ["com.sokolowska.categories"])
@EnableJpaRepositories(basePackages = ["com.sokolowska.categories"])
@EntityScan(basePackages = ["com.sokolowska.categories"])
internal open class ModuleIntegrationTestConfig {
  @Bean
  open fun objectMapper() =
    jacksonObjectMapper()
      .registerModule(ParameterNamesModule())
      .registerModule(Jdk8Module())
      .registerModule(JavaTimeModule())
}
