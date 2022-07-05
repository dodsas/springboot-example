package ys.springboot.example.quartz

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
    }

    private fun triggerFactoryBeanStyle() {
        val jobDetail = JobBuilder.newJob(SampleJobBean::class.java).withIdentity("testJobBean").build()
        jobDetail.jobDataMap["sample parameter"] = "this is sample data"

        val triggerFactory = CronTriggerFactoryBean()
        triggerFactory.setName("testJobBeanTrigger")
        triggerFactory.setCronExpression("/3 * * * * ?")
        triggerFactory.setDescription("test job")
        triggerFactory.afterPropertiesSet()
        triggerFactory.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW)

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