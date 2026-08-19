package com.sokolowska.smartbudget

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules

class ModularArchitectureTests {
  private val modules = ApplicationModules.of(SmartBudgetApplication::class.java)

  @Test
  fun `discovers the three approved application modules`() {
    assertThat(modules.getModuleByName("categories")).isPresent
    assertThat(modules.getModuleByName("expenses")).isPresent
    assertThat(modules.getModuleByName("budget")).isPresent
    assertThat(modules).hasSize(3)
  }

  @Test
  fun `module dependencies follow the declared architecture`() {
    modules.verify()
  }
}
