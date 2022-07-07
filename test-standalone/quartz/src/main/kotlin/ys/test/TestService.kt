package ys.test

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.stereotype.Service
import ys.springboot.example.quartz.YsQuartzTriggerCron
import javax.annotation.PostConstruct

@Service
class TestService(
    private val ysQuartzCron: YsQuartzTriggerCron
) {
    @PostConstruct
    fun test() {
        ysQuartzCron.scheduleCronJob("test", "/3 * * * * ?", TestJob::class.java)
    }

    class TestJob: Job {
        override fun execute(context: JobExecutionContext?) {
            println("Hello!!")
        }
    }
}