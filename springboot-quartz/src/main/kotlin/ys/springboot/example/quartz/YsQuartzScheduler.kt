package ys.springboot.example.quartz

import org.quartz.*
import org.quartz.impl.matchers.KeyMatcher
import org.springframework.scheduling.quartz.JobDetailFactoryBean
import org.springframework.stereotype.Service

@Service
class YsQuartzScheduler(
    private val scheduler: Scheduler
) {
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
        scheduler.scheduleJob(jobDetailFactory.`object`, setOf(trigger), true)
        return trigger
    }

    fun pause(alias: String){
        scheduler.pauseTrigger(getTriggerKey(alias))
    }

    fun resume(alias: String){
        scheduler.resumeTrigger(getTriggerKey(alias))
    }

    fun addJobListener(jobListener: JobListener, alias: String){
        scheduler.listenerManager.addJobListener(jobListener, KeyMatcher.keyEquals(getJobKey(alias)))
    }

    fun getJobDetail(jobKey: JobKey): JobDetail {
        return scheduler.getJobDetail(jobKey)
    }
}