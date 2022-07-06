package ys.springboot.example

import org.quartz.*
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