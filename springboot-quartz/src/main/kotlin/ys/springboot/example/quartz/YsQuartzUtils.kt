package ys.springboot.example.quartz

import org.quartz.JobKey
import org.quartz.TriggerKey

const val DEFAULT_GROUP: String = "YsQuartzGroup"

fun getTriggerKeyName(alias: String): String {
    return "YsTrigger_${alias}"
}

fun getJobDetailName(alias: String): String {
    return "YsJobDetail_${alias}"
}

fun getJobKey(alias: String): JobKey {
    return JobKey(getJobDetailName(alias), DEFAULT_GROUP)
}

fun getTriggerKey(alias: String): TriggerKey {
    return TriggerKey(getTriggerKeyName(alias), DEFAULT_GROUP)
}