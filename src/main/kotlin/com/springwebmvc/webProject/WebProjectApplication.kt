package com.springwebmvc.webProject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@ServletComponentScan
@SpringBootApplication
class WebProjectApplication {
}

fun main(args: Array<String>) {
	runApplication<WebProjectApplication>(*args)
}
