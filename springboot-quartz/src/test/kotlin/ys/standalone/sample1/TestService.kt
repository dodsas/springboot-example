package ys.standalone.sample1

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import ys.springboot.example.quartz.YsQuartzTriggerCron

@Service
class TestService(
    private val ysQuartzCron: YsQuartzTriggerCron
): InitializingBean {

    override fun afterPropertiesSet() {
        ysQuartzCron.scheduleCronJob("test", "/3 * * * * ?", TestJob::class.java)
    }

    class TestJob: Job {
        override fun execute(context: JobExecutionContext?) {
            println("Hello!!")
        }
    }
}