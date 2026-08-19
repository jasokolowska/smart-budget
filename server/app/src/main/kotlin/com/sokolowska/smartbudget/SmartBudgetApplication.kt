package com.sokolowska.smartbudget

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class SmartBudgetApplication

fun main(args: Array<String>) {
  runApplication<SmartBudgetApplication>(*args)
}
