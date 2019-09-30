package cn.todev.examples.ui

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import cn.todev.examples.R
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.activity_window.*

class WindowActivity : AppCompatActivity() {

    private lateinit var mWindowManager : WindowManager

    private var mTemperatureWindow : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)

        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        openWindow.setOnClickListener {
            openWindow()
        }

        closeWindow.setOnClickListener {
            closeWindow()
        }
    }

    fun openWindow(){
        mTemperatureWindow?: run{
            mTemperatureWindow = LayoutInflater.from(this).inflate(R.layout.item_view,null).apply {
                setOnClickListener {
                    LogUtils.e("setOnClickListener")
                }
            }

            val windowType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
            mWindowManager.addView(mTemperatureWindow, WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                    windowType, 0, PixelFormat.TRANSPARENT).apply {
                gravity = Gravity.TOP or Gravity.END
                x = 12
                y = 70
            })
        }
    }

    fun closeWindow(){
        mTemperatureWindow?.run {
            mWindowManager.removeView(mTemperatureWindow)
            mTemperatureWindow = null
        }
    }
}
