package ys.standalone.sample2

import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobListener
import java.time.LocalDateTime

class SampleListener: JobListener {
    override fun getName(): String {
        return "Sample Listener"
    }

    override fun jobToBeExecuted(context: JobExecutionContext) {
        println("================================================================")
        println("${LocalDateTime.now()}")
        println("Before")
    }

    override fun jobExecutionVetoed(context: JobExecutionContext) {
        println("Vetoed")
    }

    override fun jobWasExecuted(context: JobExecutionContext, jobException: JobExecutionException?) {
        println("After")

        if(jobException != null){
            println("failed ${jobException.message}")
        }
        else{
            println("success")
        }
        println("================================================================")
    }
}