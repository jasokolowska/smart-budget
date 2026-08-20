plugins {
  kotlin("jvm") version "2.2.21"
  kotlin("plugin.spring") version "2.2.21"
  id("org.springframework.boot") version "4.1.0"
  id("io.spring.dependency-management") version "1.1.7"
}

group = "com.sokolowska"

version = "0.0.1-SNAPSHOT"

description = "Smart Budget modular monolith"

kotlin { jvmToolchain(21) }

repositories { mavenCentral() }

dependencyManagement {
  imports { mavenBom("org.springframework.modulith:spring-modulith-bom:2.1.0") }
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.modulith:spring-modulith-core")
  implementation(kotlin("reflect"))
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.modulith:spring-modulith-starter-test")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> { useJUnitPlatform() }
