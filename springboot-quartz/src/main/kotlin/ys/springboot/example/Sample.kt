package ys.springboot.example

import org.quartz.*
import org.quartz.impl.matchers.KeyMatcher
import org.springframework.scheduling.quartz.CronTriggerFactoryBean
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class Sample(
    private val scheduler: Scheduler
){
    @PostConstruct
    fun addScheduleJob(){
        triggerBuilderStyle()
        triggerFactoryBeanStyle()
        triggerWithObjectJobData()
        triggerWithJobListener()
    }

    private fun triggerWithJobListener() {
        val jobDetail = JobBuilder.newJob(SampleJobBean::class.java).withIdentity("testJobListener").build()

        val triggerFactory = CronTriggerFactoryBean()
        triggerFactory.setName("testJobListenerTrigger")
        triggerFactory.setCronExpression("/8 * * * * ?")
        triggerFactory.afterPropertiesSet()
        scheduler.listenerManager.addJobListener(SampleListener(), KeyMatcher.keyEquals(JobKey("testJobListener")))
        scheduler.scheduleJob(jobDetail, triggerFactory.`object`)
    }

    private fun triggerWithObjectJobData() {
        val jobDetail = JobBuilder.newJob(SampleJobBean::class.java).withIdentity("testJobBeanWithObject").build()
        // If object is data, it should implement Serializable
        jobDetail.jobDataMap["sample parameter"] = SampleJobWithObjectData.JobObjectData()

        val triggerFactory = CronTriggerFactoryBean()
        triggerFactory.setName("testJobBeanWithObjectTrigger")
        triggerFactory.setCronExpression("/5 * * * * ?")
        triggerFactory.setDescription("test job with sample data")
        triggerFactory.afterPropertiesSet()

        scheduler.scheduleJob(jobDetail, triggerFactory.`object`)
    }

    private fun triggerFactoryBeanStyle() {
        val jobDetail = JobBuilder.newJob(SampleJobBean::class.java).withIdentity("testJobBean").build()
        jobDetail.jobDataMap["sample parameter"] = "this is sample data"

        val triggerFactory = CronTriggerFactoryBean()
        triggerFactory.setName("testJobBeanTrigger")
        triggerFactory.setCronExpression("/3 * * * * ?")
        triggerFactory.setDescription("test job")
        // ????????? ??? ???????????? ?????? ?????? ?????? ???????????? 0?????? ????????? (???????????????), ??????????????? ?????? ???????????? ?????? (5)
        triggerFactory.setPriority(Trigger.DEFAULT_PRIORITY)
        triggerFactory.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW)
        triggerFactory.afterPropertiesSet()

        scheduler.scheduleJob(jobDetail, triggerFactory.`object`)
    }

    private fun triggerBuilderStyle() {
        val jobDetail = JobBuilder.newJob(SampleJob::class.java).withIdentity("testJob").build()

        val trigger =
            TriggerBuilder
                .newTrigger()
                .withIdentity("testJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("/10 * * * * ?"))
                .withDescription("test job")
                .build()

        scheduler.scheduleJob(jobDetail, trigger)
    }
}