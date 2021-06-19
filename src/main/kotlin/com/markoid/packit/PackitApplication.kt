package com.markoid.packit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PackitApplication

fun main(args: Array<String>) {
	runApplication<PackitApplication>(*args)
}
