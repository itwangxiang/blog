package cn.todev.examples.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.todev.examples.R
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.activity_screen_switch.*

class ScreenSwitchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_switch)

        btnSwitch.setOnClickListener {
            if(ScreenUtils.isLandscape()) {
                ScreenUtils.setPortrait(this)
            }else {
                ScreenUtils.setLandscape(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LogUtils.e("onResume")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        LogUtils.e("onConfigurationChanged: isLandscape -> " + ScreenUtils.isLandscape())
    }
}
