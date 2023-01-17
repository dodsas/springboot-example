package ys.standalone.sample2

import org.quartz.*
import org.quartz.impl.matchers.KeyMatcher
import org.springframework.beans.factory.InitializingBean
import org.springframework.scheduling.quartz.CronTriggerFactoryBean
import org.springframework.stereotype.Service

@Service
class Sample(
    private val scheduler: Scheduler
): InitializingBean{

    override fun afterPropertiesSet() {
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
        // 팩토리 빈 스타일인 경우 해당 항목 미지정시 0으로 설정됨 (최우선순위), 빌더에서는 기본 우선순위 사용 (5)
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