package com.jinxp09277.j.library.app.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jinxp09277.j.library.app.R
import com.jinxp09277.j.library.log.*

class JLogDemoActivity : AppCompatActivity() {
    var viewPrinter: JViewPrinter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_j_log_demo)
        viewPrinter = JViewPrinter(this)
        findViewById<View>(R.id.btn_log).setOnClickListener {
            printLog()
        }
        viewPrinter!!.viewProvider.showFloatingVIew()
    }

    private fun printLog() {

        JLog.log(object : JLogConfig() {

            override fun includeTread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, JLogType.E, "-----", "5566")

        JLog.a("9900")
        JLog.a("9910")
        JLog.a("9920")
        JLog.a("9930")
    }

    override fun onPause() {
        super.onPause()
        JLogManager.getInstance().removePrinter(viewPrinter)
    }

    override fun onResume() {
        super.onResume()
        JLogManager.getInstance().addPrinter(viewPrinter)
    }
}