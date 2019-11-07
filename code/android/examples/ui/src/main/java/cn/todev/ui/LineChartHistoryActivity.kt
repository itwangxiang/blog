package cn.todev.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.activity_line_chart_history.*
import java.util.*

class LineChartHistoryActivity : AppCompatActivity() {

    private lateinit var mLineChartHelper: LineChartHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_chart_history)

        initChart()
        initListener()
    }

    fun initListener() {
        val data = mapOf(
                Calendar.getInstance().apply {
                    set(2019, 10, 6, 12, 0)
                }.timeInMillis to 1f,
                Calendar.getInstance().apply {
                    set(2019, 10, 7, 12, 0)
                }.timeInMillis to 2f,
                Calendar.getInstance().apply {
                    set(2019, 10, 7, 15, 0)
                }.timeInMillis to 1f)

        btn1.setOnClickListener {
            mLineChartHelper.setLinerChartData(data, interval = ChartInterval.CHART_DAY)
        }

        btn2.setOnClickListener {
            mLineChartHelper.setLinerChartData(data, interval = ChartInterval.CHART_WEEK)
        }

        btn3.setOnClickListener {
            mLineChartHelper.setLinerChartData(data, interval = ChartInterval.CHART_MONTH)
        }

        btn4.setOnClickListener {
            mLineChartHelper.setLinerChartData(data, interval = ChartInterval.CHART_YEAR)
        }
    }

    fun initChart() {
        mLineChartHelper = LineChartHelper(chart)
        mLineChartHelper.initChart()
        mLineChartHelper.setLinerChartMarker(object : MarkerView(this, R.layout.ui_marker_view) {

            override fun refreshContent(e: Entry, highlight: Highlight) {
                findViewById<TextView>(R.id.tvContent).text = e.y.toString()
                super.refreshContent(e, highlight)
            }

            override fun getOffset() = MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        })
        mLineChartHelper.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            var prevEntry: Entry? = null

            override fun onNothingSelected() {
                prevEntry?.icon = null
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                prevEntry?.icon = null
                e?.icon = ContextCompat.getDrawable(this@LineChartHistoryActivity, R.drawable.ic_oval)
                prevEntry = e
            }
        })
    }
}