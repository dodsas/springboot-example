package ys.standalone.sample2

import org.quartz.Job
import org.quartz.JobExecutionContext

class SampleJobWithObjectData: Job {
    override fun execute(context: JobExecutionContext) {
        val param = context.jobDetail.jobDataMap["sample parameter"]
        println("testJobWithObjectData 무야호, param = $param")
    }

    data class JobObjectData (
        val sampleData: String = "I am sample data"
    ) : java.io.Serializable
}