plugins {
  id("org.springframework.boot") version "3.5.7" apply false
  id("io.spring.dependency-management") version "1.1.7"
  kotlin("jvm") version "1.9.22"
}

group = "org.sokolowska"

version = "0.0.1-SNAPSHOT"

description = "Category Service"

configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

repositories { mavenCentral() }

dependencyManagement {
  imports { mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES) }
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")

  //    Serialization
  implementation("com.fasterxml.jackson.module:jackson-module-parameter-names")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  //    Mapper
  implementation("org.mapstruct:mapstruct:1.6.3")
  annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

  //    Tests
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.testcontainers:postgresql")
  testImplementation("org.testcontainers:junit-jupiter")

  //    Database
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.flywaydb:flyway-core")
  implementation("org.flywaydb:flyway-database-postgresql")
  runtimeOnly("org.postgresql:postgresql")

  implementation(kotlin("stdlib-jdk8"))
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> { useJUnitPlatform() }

tasks.withType<JavaCompile>().configureEach { options.compilerArgs.add("-parameters") }
