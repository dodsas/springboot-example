package ys.springboot.example

import org.quartz.Job
import org.quartz.JobExecutionContext
import kotlin.random.Random

class SampleJob: Job {
    override fun execute(context: JobExecutionContext) {
        val param = context.jobDetail.jobDataMap["sample parameter"]
        if(Random.nextBoolean()){
            println("testJob 무야호, param = $param")
        }
        else {
            println("testJob 에러야호, param = $param")
            throw RuntimeException()
        }
    }
}