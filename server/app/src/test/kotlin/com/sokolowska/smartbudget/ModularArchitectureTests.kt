package com.sokolowska.smartbudget

import com.sokolowska.smartbudget.modulithfixture.ModulithVerificationFixture
import com.tngtech.archunit.core.importer.ImportOption
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.core.Violations

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

  @Test
  fun `verification rejects an illegal module dependency`() {
    val invalidModules =
      ApplicationModules.of(ModulithVerificationFixture::class.java, ImportOption { true })

    assertThrows<Violations> { invalidModules.verify() }
  }
}
