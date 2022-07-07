package ys.springboot.example.quartz

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.quartz.Job
import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import org.quartz.PersistJobDataAfterExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class YsQuartzTest {
    @Autowired
    private lateinit var ysQuartz: YsQuartzTriggerCron

    @Autowired
    private lateinit var scheduler: YsQuartzScheduler

    companion object {
        var runCount: Int = 0
    }

    @BeforeEach
    fun beforeEach(){
        runCount = 0
    }

    @Test
    fun `단순 실행 확인`() {
        val alias = "Job For Testing 1"
        ysQuartz.scheduleCronJob(
            alias,
            "/1 * * * * ?",
            TestJob::class.java
        )

        Thread.sleep(10000)
        scheduler.pause(alias)

        assertEquals(10, runCount)
    }

    @Test
    fun `데이터 확인`() {
        val alias = "Job For Testing 2"

        val jobData = JobDataMap()
        jobData["sampleCount"] = TestJobData()

        val trigger = ysQuartz.scheduleCronJob(
            alias,
            "/1 * * * * ?",
            TestJob::class.java,
            jobData,
        )

        Thread.sleep(10000)
        scheduler.pause(alias)

        val jobDetail = scheduler.getJobDetail(trigger.jobKey)
        assertEquals(10, (jobDetail.jobDataMap["sampleCount"] as TestJobData).count)
    }

    // 요걸 붙여줘야 job data 를 업데이트 함
    @PersistJobDataAfterExecution
    class TestJob : Job {
        override fun execute(context: JobExecutionContext) {
            if(runCount != 10){
                runCount++
            }
            println("${context.trigger.key.name} 나는 테스트용 잡입니다용 $runCount")

            if (context.jobDetail.jobDataMap["sampleCount"] != null) {
                try {
                    val jobData = (context.jobDetail.jobDataMap["sampleCount"] as TestJobData)
                    if(jobData.count != 10){
                        jobData.count++
                        // 다시 put 을 해줘야 더티체킹에 걸림
                        context.jobDetail.jobDataMap["sampleCount"] = jobData
                    }
                    println("${context.trigger.key.name} 나는 테스트용 잡데이터 입니다용 ${jobData.count}")
                } catch (e: Exception) {
                    println("${context.trigger.key.name} 에러발생 띠용띠용")
                    throw e
                }
            }
        }
    }

    data class TestJobData(
        var count: Int = 0,
    ) : java.io.Serializable
}