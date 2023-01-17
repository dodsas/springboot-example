package ys.standalone.quartz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ys.springboot.example.quartz.EnableYsQuartz

@EnableYsQuartz
@SpringBootApplication
class QuartzTestApplication

fun main(args: Array<String>) {
    runApplication<QuartzTestApplication>(*args)
}