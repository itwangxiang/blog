package cn.todev.ui

import org.junit.Test

import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val mTimeFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())

    @Test
    fun addition_isCorrect() {
        println(mTimeFormat.format(getCurrentDayLastHour()))
        println(mTimeFormat.format(getCurrentWeekLastDay()))
        println(mTimeFormat.format(getCurrentMonthLastDay()))
        println(mTimeFormat.format(getCurrentYearWeekLastMonth()))

        val time1 = Calendar.getInstance().apply {
            set(2019,10,5,0,0)
        }
        val time2 = getCurrentDayLastHour()

        println(calcHourOffset(time1.time,time2))
        println(calcDayOffset(time1.time,time2))
        println(calcMonthOffset(time1.time,time2))
    }

    /**
     * 获取今天最后一小时
     */
    fun getCurrentDayLastHour() = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time

    /**
     * 获取本周的最后一天
     */
    fun getCurrentWeekLastDay() = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.DAY_OF_WEEK, 1)
        set(Calendar.DATE, get(Calendar.DATE) + 7)
    }.time

    /**
     * 获取本月最后一天
     */
    fun getCurrentMonthLastDay() = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        add(Calendar.MONTH, 1)
        set(Calendar.DAY_OF_MONTH, 0)
    }.time

    /**
     * 获取今年最后一天
     */
    fun getCurrentYearWeekLastMonth() = Calendar.getInstance().apply {
        val year = get(Calendar.YEAR)
        clear()
        set(Calendar.YEAR, year)
        roll(Calendar.DAY_OF_YEAR, -1)
    }.time

    fun calcHourOffset(date1: Date, date2: Date): Int {
        return (date2.time / 1000 / 60 / 60 - date1.time / 1000 / 60 / 60).toInt()
    }

    fun calcDayOffset(date1: Date, date2: Date): Int {
        val cal1 = Calendar.getInstance()
        cal1.time = date1

        val cal2 = Calendar.getInstance()
        cal2.time = date2
        val day1 = cal1.get(Calendar.DAY_OF_YEAR)
        val day2 = cal2.get(Calendar.DAY_OF_YEAR)

        val year1 = cal1.get(Calendar.YEAR)
        val year2 = cal2.get(Calendar.YEAR)
        if (year1 != year2) {
            var timeDistance = 0
            for (i in year1 until year2) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {  //闰年
                    timeDistance += 366
                } else {  //不是闰年

                    timeDistance += 365
                }
            }
            return timeDistance + (day2 - day1)
        } else {
            return day2 - day1
        }
    }

    fun calcMonthOffset(date1: Date, date2: Date): Int {
        var iMonth = 0
        var flag = 0
        try {
            var objCalendarDate1 = Calendar.getInstance()
            objCalendarDate1.time = date1

            var objCalendarDate2 = Calendar.getInstance()
            objCalendarDate2.time = date2

            if (objCalendarDate2 == objCalendarDate1)
                return 0
            if (objCalendarDate1.after(objCalendarDate2)) {
                val temp = objCalendarDate1
                objCalendarDate1 = objCalendarDate2
                objCalendarDate2 = temp
            }
            if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
                            .get(Calendar.DAY_OF_MONTH)
            )
                flag = 1

            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
                            .get(Calendar.YEAR)
            )
                iMonth = (objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
                        .get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - flag - objCalendarDate1.get(
                        Calendar.MONTH
                )
            else
                iMonth = (objCalendarDate2.get(Calendar.MONTH)
                        - objCalendarDate1.get(Calendar.MONTH) - flag)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return iMonth
    }
}
