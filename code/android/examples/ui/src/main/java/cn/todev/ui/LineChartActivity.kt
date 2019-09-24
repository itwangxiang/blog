package cn.todev.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.LogUtils
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_line_chart.*
import java.text.SimpleDateFormat
import java.util.*

class LineChartActivity : AppCompatActivity() {

    private val mLineChartDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private var mLineChartInterval = 2//数据点间隔，默认 2 s
    private var mLineChartCount = 60 / mLineChartInterval * 60 //最大数据点
    private var mLineChartUpdateTime = System.currentTimeMillis()
    private var mLineChartValues = mutableMapOf<Long, Int>()
    private lateinit var mLineDataSetTime: LineDataSet
    private lateinit var mLineDataSetData: LineDataSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_chart)

        initLinerChart()

        btnTwo.setOnClickListener {
            setLineChartVisibleCount(mLineChartCount/30)
        }

        btnTen.setOnClickListener {
            setLineChartVisibleCount(mLineChartCount/6)
        }

        btn30.setOnClickListener {
            setLineChartVisibleCount(mLineChartCount / 2)
        }

        btn60.setOnClickListener {
            setLineChartVisibleCount(mLineChartCount)
        }

        Thread {
            while (true) {
                runOnUiThread {
                    val data = Random().nextInt(399 - 361) + 361
                    notifyLineChartRefreshData(data)
                }
                Thread.sleep(1000)
            }
        }.start()
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
                e?.icon = ContextCompat.getDrawable(this@LineChartActivity, R.drawable.ic_oval)
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

            //设置X轴值
            valueFormatter = IAxisValueFormatter { value, _ ->
                mLineChartDateFormat.format(mLineChartUpdateTime - (mLineChartCount - value.toInt()) * mLineChartInterval * 1000)
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

            axisMinimum = 350f
            axisMaximum = 420f
            enableGridDashedLine(5f, 2f, 0f)
            addLimitLine(LimitLine(385f).apply {
                lineColor = Color.parseColor("#FF3912")
                enableDashedLine(10f, 10f, 0f)
            })
            addLimitLine(LimitLine(375f).apply {
                lineColor = Color.parseColor("#00E69D")
                enableDashedLine(10f, 10f, 0f)
            })
        }

        //Y轴(右)
        chart.axisRight.isEnabled = false

        //设置时间标签
        mLineDataSetTime = LineDataSet(mutableListOf(), null)
        //设置数据标签
        mLineDataSetData = LineDataSet(mutableListOf(), null)
                .apply {
                    color = Color.parseColor("#FFA73A")
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    lineWidth = 1.5f
                    setDrawCircles(false) //设置是否绘制圆形指示器
                    setDrawValues(false) //是否绘制数据值
                    setDrawHighlightIndicators(false) //设置是否有拖拽高亮指示器
                }

        repeat(mLineChartCount) {
            mLineDataSetTime.addEntry(Entry(it.toFloat(), 0f))
        }

        chart.data = LineData(mLineDataSetTime, mLineDataSetData).apply {
            isHighlightEnabled = true
        }

        chart.invalidate()
        setLineChartVisibleCount(mLineChartCount / 30)
    }

    private fun setLineChartVisibleCount(showCount: Int) {
        chart.fitScreen()
        chart.setVisibleXRangeMaximum(if (showCount > mLineChartCount) mLineChartCount.toFloat() else showCount.toFloat())
        chart.moveViewToX((mLineChartCount - 1).toFloat())
        chart.highlighted?.takeIf { !it.isNullOrEmpty() }?.run {
            chart.moveViewToX(this[0].x)
        }
    }

    private fun notifyLineChartRefreshData(data: Int) {
        val nowTime = System.currentTimeMillis()

        mLineChartUpdateTime = nowTime
        mLineChartValues[nowTime / 1000] = data

        var highlightTime = 0L
        var highlightRefreshX = 0F
        chart.highlighted?.takeIf { !it.isNullOrEmpty() }?.run {
            val highlight = this[0]
            mLineDataSetData.getEntriesForXValue(highlight.x)?.takeIf { !it.isNullOrEmpty() }?.run {
                highlightTime = this[0].data as Long
            }
        }

        mLineDataSetData.clear()
        repeat(mLineChartCount) {
            val time = mLineChartUpdateTime / 1000 - (mLineChartCount - it)
            if (mLineChartValues.containsKey(time)) {
                mLineDataSetData.addEntry(Entry(it.toFloat(), mLineChartValues[time]!!.toFloat(), time))
                if (time == highlightTime) highlightRefreshX = it.toFloat()
            }
        }

        if (highlightRefreshX <= 0) {
            chart.highlightValues(null)
        } else {
            chart.highlightValue(highlightRefreshX, 1)
        }
    }

}