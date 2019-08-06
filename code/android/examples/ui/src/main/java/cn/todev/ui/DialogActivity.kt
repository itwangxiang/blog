package cn.todev.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        btnDialog.setOnClickListener {
            Dialog(this, R.style.dialog_corners).run {
                setContentView(R.layout.dialog_demo)
                show()
            }
        }
    }
}
