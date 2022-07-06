package ys.springboot.example

import org.quartz.*
import org.quartz.JobKey.jobKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean

import org.quartz.impl.matchers.KeyMatcher.*

@Configuration
class SampleScheduler{

    @Bean
    // cannot autowired factory is idea tool bug. that bean will be autowired by QuartzAutoConfiguration
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    fun scheduler(factory: SchedulerFactoryBean): Scheduler {
        val scheduler = factory.scheduler

        // Examples http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-07.html
        // scheduler.getListenerManager().addJobListener(myJobListener, jobKeyEquals(jobKey("myJobName", "myJobGroup")));
        // scheduler.getListenerManager().addJobListener(myJobListener, jobGroupEquals("myJobGroup"));
        scheduler.listenerManager.addJobListener(SampleListener(), keyEquals(jobKey("testJob")))
        return scheduler
    }
}