package ys.springboot.example.quartz

import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean

// Bean 주입은 이미 SpringBeanJobFactory 에서 별도 추가해놔서 불필요, QuartzJobBean 대신 Job 사용하면 됨
class SampleJobBean: QuartzJobBean() {
    override fun executeInternal(context: JobExecutionContext) {
        val param = context.jobDetail.jobDataMap["sample parameter"]
        println("testJobBean 무야호, param = $param")
    }
}