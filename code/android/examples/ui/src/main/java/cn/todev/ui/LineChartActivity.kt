package cn.todev.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import kotlinx.android.synthetic.main.activity_line_chart.*
import java.text.SimpleDateFormat
import java.util.*

class LineChartActivity : AppCompatActivity() {

    private val mSdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    private val mLineChartDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private var mLineChartCount = 120
    private var mLineChartRefreshTime = System.currentTimeMillis()
    private var mLineChartValues = mutableMapOf<Long, Int>()
    private lateinit var mLineDataSetTime: LineDataSet
    private lateinit var mLineDataSetData: LineDataSet
    private var mLineDataSetTimeEntryList = mutableListOf<Entry>()
    private var mLineDataSetDataEntryList = mutableListOf<Entry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_chart)

        initChart()

        btnTwo.setOnClickListener {
            setLinerChartXCount(120)
        }

        btnTen.setOnClickListener {
            setLinerChartXCount(600)
        }
    }

    private fun initChart() {
        chart.description = null
        //设置图例关
        chart.legend.isEnabled = false
        //显示边界
        chart.setDrawBorders(false)
        //设置显示范围
        chart.setVisibleXRangeMaximum(1f)
        chart.setVisibleYRangeMinimum(10f, YAxis.AxisDependency.LEFT)

        //设置透明度
        chart.alpha = 1.0f
        //设置背景色
        chart.setBackgroundColor(Color.WHITE)
        //设置触摸(关闭影响下面3个属性)
        chart.setTouchEnabled(true)
        //设置是否可以拖拽
        chart.isDragEnabled = false
        //设置是否可以缩放
        chart.setScaleEnabled(false)
        //设置是否能扩大扩小
        chart.setPinchZoom(false)
        //隐藏点击数据点时的高亮十字
        chart.isHighlightPerTapEnabled = true
        chart.isHighlightPerDragEnabled = true

        //X轴
        chart.xAxis.run {
            isEnabled = true
            axisLineWidth = 1f
            axisLineColor = Color.BLACK
            //设置X轴避免图表或屏幕的边缘的第一个和最后一个轴中的标签条目被裁剪
            setAvoidFirstLastClipping(false)
            //设置X轴底部显示
            position = XAxis.XAxisPosition.BOTTOM
            //设置竖网格
            setDrawGridLines(false)
            setDrawAxisLine(false)
            labelCount = 3

            //设置X轴文字
            textSize = 9f
            textColor = Color.BLACK

            //设置X轴单位间隔
            granularity = 1f
            //设置X轴值
            valueFormatter = IAxisValueFormatter { value, _ ->
                mLineChartDateFormat.format(mLineChartRefreshTime - (mLineChartCount - value.toInt()) * 1000)
            }
        }

        //Y轴(左)
        chart.axisLeft.run {
            setDrawAxisLine(false)
            textColor = Color.parseColor("#434B61")
            textSize = 18f
            axisLineColor = Color.parseColor("#545C70")
            axisLineWidth = 1f
            valueFormatter = IAxisValueFormatter { value, _ -> String.format("%.1f", value / 10) }

            granularity = 1f

            axisMinimum = 301f
            axisMaximum = 429f

            //设置Y轴文字在外部显示
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            enableGridDashedLine(5f, 2f, 0f)
            addLimitLine(LimitLine(385f).apply {
                lineColor = Color.parseColor("#FF3912")
                enableDashedLine(10f, 10f, 0f)
            })
            addLimitLine(LimitLine(370f).apply {
                lineColor = Color.parseColor("#00E69D")
                enableDashedLine(10f, 10f, 0f)
            })
        }

        //Y轴(右)
        chart.axisRight.isEnabled = false

        //设置时间标签
        mLineDataSetTime = LineDataSet(mLineDataSetTimeEntryList, "")
                .apply {
                    color = Color.parseColor("#ffffff")
                    setDrawCircles(false)
                }
        //设置数据标签
        mLineDataSetData = LineDataSet(mLineDataSetDataEntryList, "当前温度统计曲线（时/分/秒）")
                .apply {
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    color = Color.parseColor("#FFA73A")
                    setDrawCircles(false)
                    setDrawValues(false)
                }

        chart.data = LineData(mLineDataSetTime, mLineDataSetData)
        chart.invalidate()

        //设置默认数据
        setLinerChartXCount(120)

        Thread {
            while (true) {
                Thread.sleep(1000)

                runOnUiThread {
                    val data = Random().nextInt(399 - 361) + 361
                    addLinerChartData(data)
                    refreshLinerChartData()
                }
            }
        }.start()
    }

    private fun addLinerChartData(data: Int) {
        mLineChartValues[System.currentTimeMillis() / 1000] = data
    }

    private fun setLinerChartXCount(count: Int) {
        mLineChartCount = count
        mLineChartRefreshTime = System.currentTimeMillis()

        mLineDataSetTimeEntryList.clear()
        mLineDataSetDataEntryList.clear()

        for (i in 0..mLineChartCount) {
            mLineDataSetTimeEntryList.add(Entry(i.toFloat(), 0f))
            val time = mLineChartRefreshTime / 1000 - (mLineChartCount - i)
            if (mLineChartValues.containsKey(time)) {
                mLineDataSetDataEntryList.add(Entry(i.toFloat(), mLineChartValues[time]!!.toFloat()))
            }
        }

        mLineDataSetTime.notifyDataSetChanged()
        mLineDataSetData.notifyDataSetChanged()
        chart.data.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()
    }

    private fun refreshLinerChartData() {
        mLineChartRefreshTime = System.currentTimeMillis()
        mLineDataSetDataEntryList.clear()
        for (i in 0..mLineChartCount) {
            val time = mLineChartRefreshTime / 1000 - (mLineChartCount - i)
            if (mLineChartValues.containsKey(time)) {
                mLineDataSetDataEntryList.add(Entry(i.toFloat(), mLineChartValues[time]!!.toFloat()))
            }
        }
        chart.invalidate()
    }

}

data class Temperature(var bodyTemperature: Int, var measureTime: Long, var date: String)