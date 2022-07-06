package ys.springboot.example.quartz

import org.quartz.*
import org.springframework.scheduling.quartz.CronTriggerFactoryBean
import org.springframework.stereotype.Service

@Service
class YsQuartzCron(
    scheduler: Scheduler,
) : YsQuartzAbstract(scheduler) {

    fun scheduleCronJob(
        alias: String,
        cronExpression: String,
        jobClass: Class<out Job>,
        jobDataMap: JobDataMap? = null,
        priority: Int = Trigger.DEFAULT_PRIORITY,
        description: String = "No Description",
    ): CronTrigger {
        val triggerFactory = CronTriggerFactoryBean()
        triggerFactory.setName(getTriggerKeyName(alias))
        triggerFactory.setGroup(DEFAULT_GROUP)
        triggerFactory.setCronExpression(cronExpression)
        triggerFactory.setDescription(description)
        triggerFactory.setPriority(priority)
        triggerFactory.afterPropertiesSet()

        return scheduleJob(alias, jobClass, triggerFactory.`object`!!, jobDataMap, description) as CronTrigger
    }
}