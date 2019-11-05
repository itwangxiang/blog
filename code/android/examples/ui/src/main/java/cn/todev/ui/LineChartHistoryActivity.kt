package cn.todev.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_line_chart_history.*
import java.util.*

class LineChartHistoryActivity : AppCompatActivity() {

    private lateinit var mLineChartHelper: LineChartHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_chart_history)

        mLineChartHelper = LineChartHelper(this, chart)
        mLineChartHelper.init(0f, 12f)

        val data = mapOf(
                Calendar.getInstance().apply {
                    set(2019, 10, 4, 0, 15)
                }.timeInMillis to 6.5f,
                Calendar.getInstance().apply {
                    set(2019, 10, 4, 23, 15)
                }.timeInMillis to 8f,
                Calendar.getInstance().apply {
                    set(2019, 10, 5, 0, 15)
                }.timeInMillis to 6f,
                Calendar.getInstance().apply {
                    set(2019, 10, 5, 23, 15)
                }.timeInMillis to 7f
        )

        mLineChartHelper.setLinerData()

        btn1.setOnClickListener {
            mLineChartHelper.setLinerData(data, interval = ChartInterval.CHART_DAY)
        }

        btn2.setOnClickListener {
            mLineChartHelper.setLinerData(data, interval = ChartInterval.CHART_WEEK)
        }

        btn3.setOnClickListener {
            mLineChartHelper.setLinerData(interval = ChartInterval.CHART_DAY)
        }

        btn4.setOnClickListener {
            mLineChartHelper.setLinerData(data, interval = ChartInterval.CHART_YEAR)
        }
    }
}