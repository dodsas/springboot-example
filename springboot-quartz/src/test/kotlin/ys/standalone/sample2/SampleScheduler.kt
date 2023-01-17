package ys.standalone.sample2

//Move To YsQuartzConfiguration

//@Configuration
//class SampleScheduler{
//
//    @Bean
//    // cannot autowired factory is idea tool bug. that bean will be autowired by QuartzAutoConfiguration
//    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
//    fun scheduler(factory: SchedulerFactoryBean): Scheduler {
//        val scheduler = factory.scheduler
//
//        // Examples http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-07.html
//        // scheduler.getListenerManager().addJobListener(myJobListener, jobKeyEquals(jobKey("myJobName", "myJobGroup")));
//        // scheduler.getListenerManager().addJobListener(myJobListener, jobGroupEquals("myJobGroup"));
//        scheduler.listenerManager.addJobListener(SampleListener(), keyEquals(jobKey("testJob")))
//        return scheduler
//    }
//}