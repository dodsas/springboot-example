package ys.springboot.example

import org.quartz.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean

@Configuration
class SampleScheduler{

    @Bean
    // cannot autowired factory is idea tool bug. that bean will be autowired by QuartzAutoConfiguration
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    fun scheduler(factory: SchedulerFactoryBean): Scheduler {
        val scheduler = factory.scheduler
        scheduler.listenerManager.addJobListener(SampleListener())
        return scheduler
    }
}