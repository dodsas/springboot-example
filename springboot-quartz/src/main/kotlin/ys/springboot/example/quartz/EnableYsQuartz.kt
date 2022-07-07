package ys.springboot.example.quartz

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(
    YsQuartzConfiguration::class,
    YsQuartzTriggerCron::class,
    YsQuartzScheduler::class,
)
annotation class EnableYsQuartz
