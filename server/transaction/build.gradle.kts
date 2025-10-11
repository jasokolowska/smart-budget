plugins {
  java
  id("org.springframework.boot") version "3.5.6" apply false
  id("io.spring.dependency-management") version "1.1.7"
}

group = "com.sokolowska"

version = "0.0.1-SNAPSHOT"

description = "Transactions Service"

java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

configurations { compileOnly { extendsFrom(configurations.annotationProcessor.get()) } }

repositories { mavenCentral() }

dependencyManagement {
  imports { mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES) }
}

dependencies {
  implementation("org.springframework.boot:spring-boot")
  implementation("org.springframework.boot:spring-boot-starter-web")
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> { useJUnitPlatform() }

tasks.withType<JavaCompile>().configureEach { options.compilerArgs.add("-parameters") }
