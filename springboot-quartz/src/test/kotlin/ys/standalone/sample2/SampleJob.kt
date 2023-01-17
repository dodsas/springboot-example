package ys.standalone.sample2

import org.quartz.Job
import org.quartz.JobExecutionContext
import kotlin.random.Random

class SampleJob: Job {
    override fun execute(context: JobExecutionContext) {
        val param = context.jobDetail.jobDataMap["sample parameter"]
        if(Random.nextBoolean()){
            println("${context.jobDetail.key.name} 무야호, param = $param")
        }
        else {
            println("${context.jobDetail.key.name} 에러야호, param = $param")
            throw RuntimeException()
        }
    }
}