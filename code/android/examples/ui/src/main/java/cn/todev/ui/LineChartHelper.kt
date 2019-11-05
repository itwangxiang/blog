package cn.todev.ui

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class LineChartHelper(val context: Context, private val chart: LineChart) {

    private var mHourFormat = SimpleDateFormat("M/d HH", Locale.getDefault())
    private var mDayFormat = SimpleDateFormat("M月d日", Locale.getDefault())
    private var mYearFormat = SimpleDateFormat("yyyy/MM", Locale.getDefault())

    private var xInterval = ChartInterval.CHART_DAY
    private var xMaxCount = getXDefaultCount(xInterval) //X 轴的最大数

    //设置时间标签
    private var mLineDataSetTime: LineDataSet = LineDataSet(mutableListOf(), null)

    //设置数据标签
    private var mLineDataSetValue: LineDataSet = LineDataSet(mutableListOf(), null)
            .apply {
                color = Color.parseColor("#FF62DBB3")
                mode = LineDataSet.Mode.CUBIC_BEZIER
                lineWidth = 1.5f
                setDrawCircles(false) //设置是否绘制圆形指示器
                setDrawValues(false) //是否绘制数据值
                setDrawHighlightIndicators(false) //设置是否有拖拽高亮指示器
            }

    fun init(
            yMin: Float? = 0f,
            yMax: Float? = null,
            yFormat: (value: Float) -> String = { it.toString() },
            xHourPattern: String = "M/d HH",
            xDayPattern: String = "M月d日",
            xYearPattern: String = "yyyy/MM"
    ) {

        mHourFormat = SimpleDateFormat(xHourPattern, Locale.getDefault())
        mDayFormat = SimpleDateFormat(xDayPattern, Locale.getDefault())
        mYearFormat = SimpleDateFormat(xYearPattern, Locale.getDefault())

        chart.description = null //设置描写
        chart.legend.isEnabled = false //设置图例关
        chart.setDrawBorders(false) //设置是否显示边界
        chart.setBackgroundColor(Color.WHITE) //设置背景色

        //设置触摸(关闭影响下面3个属性)
        chart.setTouchEnabled(true)
        //设置是否可以拖拽
        chart.isDragEnabled = true
        //设置是否可以缩放
        chart.setScaleEnabled(false)
        chart.isScaleXEnabled = false
        //设置是否能扩大扩小
        chart.setPinchZoom(false)

        chart.marker = object : MarkerView(context, R.layout.ui_marker_view) {

            var tvContent: TextView = findViewById(R.id.tvContent)

            override fun refreshContent(e: Entry, highlight: Highlight) {
                tvContent.text = yFormat(e.y)
                super.refreshContent(e, highlight)
            }

            override fun getOffset() = MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }

        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            var prevEntry: Entry? = null

            override fun onNothingSelected() {
                prevEntry?.icon = null
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                prevEntry?.icon = null
                e?.icon = ContextCompat.getDrawable(context, R.drawable.ic_oval)
                prevEntry = e
            }
        })

        //X轴
        chart.xAxis.run {
            //设置竖网格
            setDrawGridLines(false)
            //设置X轴线
            setDrawAxisLine(false)
            //设置间隔
            granularity = 1f
            //设置X轴文字在底部显示
            position = XAxis.XAxisPosition.BOTTOM
            //设置X轴文字
            textColor = Color.parseColor("#434B61")
            textSize = 9f
            //设置X轴避免图表或屏幕的边缘的第一个和最后一个轴中的标签条目被裁剪
            setAvoidFirstLastClipping(true)

            //设置X轴值
            valueFormatter = IAxisValueFormatter { value, _ -> getXLabelByXValue(value) }
        }

        //Y轴(左)
        chart.axisLeft.run {
            //设置Y轴线
            setDrawAxisLine(false)
            //设置Y轴文字在内部显示
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)

            yMin?.let { axisMinimum = it }
            yMax?.let { axisMaximum = it }

            //设置Y轴文字
            textColor = Color.parseColor("#FF434B61")
            textSize = 10f
            valueFormatter = IAxisValueFormatter { value, _ -> yFormat(value) }

            enableGridDashedLine(5f, 2f, 0f)
        }

        //Y轴(右)
        chart.axisRight.isEnabled = false

        chart.data = LineData(mLineDataSetTime, mLineDataSetValue).apply {
            isHighlightEnabled = true
        }

        chart.invalidate()
    }

    fun setLinerData(data: Map<Long, Float> = mapOf(), interval: ChartInterval = xInterval) {
        xInterval = interval

        mLineDataSetTime.clear()
        mLineDataSetValue.clear()

        xMaxCount = getXDefaultCount(interval)

        val dataMapByIndex = mutableMapOf<Int, Float>()
        if (data.isNotEmpty()) {
            val minTime = data.minBy { it.key }!!
            val offset = calcMinOffset(interval, Date(minTime.key)) + 1
            if (offset > xMaxCount) xMaxCount = offset

            data.entries.sortedBy { it.key }.forEach {
                val curOffset = calcMinOffset(interval, Date(it.key)) + 1
                dataMapByIndex[xMaxCount - curOffset] = it.value
            }
        }

        repeat(xMaxCount) {
            mLineDataSetTime.addEntry(Entry(it.toFloat(), -1f))
            if (dataMapByIndex.containsKey(it)) {
                mLineDataSetValue.addEntry(Entry(it.toFloat(), dataMapByIndex[it]!!))
            }
        }

        chart.xAxis.setLabelCount(getXLabelCountByInterval(interval), false)

        mLineDataSetTime.notifyDataSetChanged()
        mLineDataSetValue.notifyDataSetChanged()
        chart.data.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()

        chart.fitScreen()
        chart.setVisibleXRangeMaximum(getXDefaultCount(interval).toFloat() - 1)
        chart.moveViewToX(xMaxCount.toFloat() - 1)
    }

    /**
     * 获取当前时间的间隔数 （天：24小时，周：7天，月：计算得出，年：12月）
     */
    private fun getXDefaultCount(interval: ChartInterval = xInterval) = when (interval) {
        ChartInterval.CHART_DAY -> 24
        ChartInterval.CHART_WEEK -> 7
        ChartInterval.CHART_MONTH -> Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
        ChartInterval.CHART_YEAR -> 12
    }

    /**
     * 获取 X 轴描述，根据 X 轴的值
     */
    private fun getXLabelByXValue(value: Float, interval: ChartInterval = xInterval): String {
        val offset = xMaxCount - 1 - value.toInt()
        return when (interval) {
            ChartInterval.CHART_DAY -> with(getCurrentDayLastHour()) {
                add(Calendar.HOUR_OF_DAY, -offset)
                mHourFormat.format(time)
            }
            ChartInterval.CHART_WEEK -> with(getCurrentWeekLastDay()) {
                add(Calendar.DATE, -offset)
                mDayFormat.format(time)
            }
            ChartInterval.CHART_MONTH -> with(getCurrentMonthLastDay()) {
                add(Calendar.DATE, -offset)
                mDayFormat.format(time)
            }
            ChartInterval.CHART_YEAR -> with(getCurrentYearWeekLastMonth()) {
                add(Calendar.MONTH, -offset)
                mYearFormat.format(time)
            }
        }
    }

    private fun getXLabelCountByInterval(interval: ChartInterval = xInterval) = when (interval) {
        ChartInterval.CHART_DAY -> 12
        ChartInterval.CHART_WEEK -> 7
        ChartInterval.CHART_MONTH -> 12
        ChartInterval.CHART_YEAR -> 12
    }

    /**
     * 计算当前间隔最大时间与入参时间的差值
     */
    private fun calcMinOffset(interval: ChartInterval, time: Date): Int {
        return when (interval) {
            ChartInterval.CHART_DAY -> {
                calcHourOffset(time, getCurrentDayLastHour().time)
            }
            ChartInterval.CHART_WEEK -> {
                calcDayOffset(time, getCurrentWeekLastDay().time)
            }
            ChartInterval.CHART_MONTH -> {
                calcDayOffset(time, getCurrentMonthLastDay().time)
            }
            ChartInterval.CHART_YEAR -> {
                calcMonthOffset(time, getCurrentYearWeekLastMonth().time)
            }
        }
    }

    /**
     * 获取今天最后一小时
     */
    private fun getCurrentDayLastHour() = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    /**
     * 获取本周的最后一天
     */
    private fun getCurrentWeekLastDay() = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.DAY_OF_WEEK, 1)
        set(Calendar.DATE, get(Calendar.DATE) + 7)
    }

    /**
     * 获取本月最后一天
     */
    private fun getCurrentMonthLastDay() = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        add(Calendar.MONTH, 1)
        set(Calendar.DAY_OF_MONTH, 0)
    }

    /**
     * 获取今年最后一天
     */
    fun getCurrentYearWeekLastMonth() = Calendar.getInstance().apply {
        val year = get(Calendar.YEAR)
        clear()
        set(Calendar.YEAR, year)
        roll(Calendar.DAY_OF_YEAR, -1)
    }

    private fun calcHourOffset(date1: Date, date2: Date): Int {
        return (date2.time / 1000 / 60 / 60 - date1.time / 1000 / 60 / 60).toInt()
    }

    private fun calcDayOffset(date1: Date, date2: Date): Int {
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

    private fun calcMonthOffset(date1: Date, date2: Date): Int {
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

enum class ChartInterval {
    CHART_DAY,
    CHART_WEEK,
    CHART_MONTH,
    CHART_YEAR
}