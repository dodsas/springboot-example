package ys.springboot.example.quartz

import org.quartz.*
import org.quartz.impl.matchers.KeyMatcher.keyEquals
import org.springframework.scheduling.quartz.JobDetailFactoryBean

abstract class YsQuartzAbstract(
    private val scheduler: Scheduler,
) {
    companion object {
        const val DEFAULT_GROUP: String = "YsQuartzGroup"
    }

    fun pause(alias: String){
        scheduler.pauseTrigger(getTriggerKey(alias))
    }

    fun resume(alias: String){
        scheduler.resumeTrigger(getTriggerKey(alias))
    }

    fun addJobListener(jobListener: JobListener, alias: String){
        scheduler.listenerManager.addJobListener(jobListener, keyEquals(getJobKey(alias)))
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

    fun getTriggerKeyName(alias: String): String{
        return "YsTrigger_${alias}"
    }

    fun getJobDetailName(alias: String): String{
        return "YsJobDetail_${alias}"
    }

    fun getJobKey(alias: String): JobKey {
        return JobKey(getJobDetailName(alias), DEFAULT_GROUP)
    }

    fun getTriggerKey(alias: String): TriggerKey {
        return TriggerKey(getTriggerKeyName(alias), DEFAULT_GROUP)
    }

    fun getJobDetail(jobKey: JobKey): JobDetail {
        return scheduler.getJobDetail(jobKey)
    }
}