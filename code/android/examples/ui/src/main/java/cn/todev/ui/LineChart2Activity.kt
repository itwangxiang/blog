package cn.todev.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_line_chart2.*
import java.text.SimpleDateFormat
import java.util.*

class LineChart2Activity : AppCompatActivity() {

    private val mLineChartDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
    private var mDefaultCount = 60 / 30 * 24
    private var mLineChartCount = mDefaultCount
    private lateinit var mLineDataSetData: LineDataSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_chart2)

        initLinerChart()

        btn1.setOnClickListener {
            mLineChartCount = mDefaultCount
            setLineChartVisibleCount()
        }

        btn2.setOnClickListener {
            mLineChartCount = mDefaultCount * 7
            setLineChartVisibleCount()
        }

        btn3.setOnClickListener {
            mLineChartCount = mDefaultCount * 30
            setLineChartVisibleCount()
        }
    }

    private fun initLinerChart() {
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

        chart.marker = MyMarkerView(this, R.layout.ui_marker_view).apply { chartView = chart }
        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            var prevEntry: Entry? = null

            override fun onNothingSelected() {
                prevEntry?.icon = null
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                prevEntry?.icon = null
                e?.icon = ContextCompat.getDrawable(this@LineChart2Activity, R.drawable.ic_oval)
                prevEntry = e
            }
        })

        //X轴
        chart.xAxis.run {
            //设置竖网格
            setDrawGridLines(false)
            //设置X轴线
            setDrawAxisLine(false)
            //设置X轴文字在底部显示
            position = XAxis.XAxisPosition.BOTTOM
            //设置X轴文字
            textColor = Color.parseColor("#434B61")
            textSize = 9f
            //设置X轴避免图表或屏幕的边缘的第一个和最后一个轴中的标签条目被裁剪
            setAvoidFirstLastClipping(true)

            labelCount = 3

            //设置X轴值
            valueFormatter = IAxisValueFormatter { value, _ ->
                try {
                    val temperature = mLineDataSetData.getEntriesForXValue(value)[0].data as Temperature
                    mLineChartDateFormat.format(temperature.measureTime)
                } catch (e: Exception) {
                    e.printStackTrace()
                    "nil"
                }
            }
        }

        //Y轴(左)
        chart.axisLeft.run {
            //设置Y轴线
            setDrawAxisLine(false)
            //设置Y轴文字在内部显示
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            //设置Y轴文字
            textColor = Color.parseColor("#434B61")
            textSize = 12f
            valueFormatter = IAxisValueFormatter { value, _ -> String.format("%.1f", value / 10) }

            axisMinimum = 301f
            axisMaximum = 429f
            enableGridDashedLine(5f, 2f, 0f)
        }

        //Y轴(右)
        chart.axisRight.isEnabled = false

        //设置数据标签
        mLineDataSetData = LineDataSet(mutableListOf(), null)
                .apply {
                    valueFormatter = IValueFormatter { value, _, _, _ -> String.format("%.1f", value / 10) }
                    color = Color.parseColor("#FFA73A")
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    lineWidth = 1.5f
                    setDrawCircles(false)
                    setDrawValues(false)
                }

        chart.data = LineData(mLineDataSetData).apply {
            isHighlightEnabled = true
        }

        initData()
    }

    private fun setLineChartVisibleCount() {
        chart.fitScreen()
        val allCount = mLineDataSetData.entryCount
        chart.setVisibleXRangeMaximum(if (mLineChartCount > allCount) allCount.toFloat() else mLineChartCount.toFloat())
        chart.highlighted?.takeIf { !it.isNullOrEmpty() }?.run {
            chart.moveViewToX(this[0].x)
        }
    }

    private fun setLineChartData(temperatures: List<Temperature>) {
        mLineDataSetData.clear()

        temperatures.forEachIndexed { index, temperature ->
            mLineDataSetData.addEntry(Entry(index.toFloat(), (temperature.bodyTemperature / 10).toFloat(), temperature))
        }

        mLineDataSetData.notifyDataSetChanged()
        chart.data.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.highlightValues(null)

        setLineChartVisibleCount()
    }

    private fun initData() {
        val temperatures = mutableListOf<Temperature>()

        val calendar = Calendar.getInstance()
        calendar.set(2018, 6, 20)
        repeat(60 / 30 * 24 * 60) {
            //            val data = Random().nextInt(399 - 361) + 361
            temperatures.add(Temperature(3800, calendar.timeInMillis))
            calendar.add(Calendar.MINUTE, 30)
        }

        setLineChartData(temperatures)
    }
}

data class Temperature(var bodyTemperature: Int, var measureTime: Long)