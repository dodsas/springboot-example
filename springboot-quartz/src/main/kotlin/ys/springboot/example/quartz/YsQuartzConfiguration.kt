package ys.springboot.example.quartz

import org.quartz.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean

@Configuration
class YsQuartzConfiguration{

    @Bean
    // cannot autowired factory is idea tool bug. that bean will be autowired by QuartzAutoConfiguration
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    fun scheduler(factory: SchedulerFactoryBean): Scheduler {
        return factory.scheduler
    }
}