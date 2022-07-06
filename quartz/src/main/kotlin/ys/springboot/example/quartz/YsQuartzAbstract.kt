package ys.springboot.example.quartz

import org.quartz.*
import org.springframework.scheduling.quartz.JobDetailFactoryBean

abstract class YsQuartzAbstract(
    private val scheduler: Scheduler,
) {
    companion object {
        const val DEFAULT_GROUP: String = "YsDefaultGroup"
    }

    fun pause(alias: String){
        scheduler.pauseTrigger(TriggerKey(getTriggerKeyName(alias), DEFAULT_GROUP))
    }

    fun resume(alias: String){
        scheduler.resumeTrigger(TriggerKey(getTriggerKeyName(alias), DEFAULT_GROUP))
    }

    fun scheduleJob(
        alias: String,
        jobClass: Class<out Job>,
        trigger: Trigger,
        jobDataMap: JobDataMap? = null,
        description: String = "No Description",
    ): Trigger {
        val jobDetailFactory = JobDetailFactoryBean()
        jobDataMap?.let { jobDetailFactory.jobDataMap = jobDataMap }
        jobDetailFactory.setJobClass(jobClass)
        jobDetailFactory.setGroup(DEFAULT_GROUP)
        jobDetailFactory.setName(getJobDetailName(alias))
        jobDetailFactory.setDescription(description)
        jobDetailFactory.afterPropertiesSet()
        scheduler.scheduleJob(jobDetailFactory.`object`, trigger)
        return trigger
    }

    protected fun getTriggerKeyName(alias: String): String{
        return "YsTrigger_${alias}"
    }

    protected fun getJobDetailName(alias: String): String{
        return "YsJobDetail_${alias}"
    }

    fun getJobDetail(jobKey: JobKey): JobDetail {
        return scheduler.getJobDetail(jobKey)
    }
}