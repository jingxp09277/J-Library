package com.jinxp09277.j.library.app.demo.log

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jinxp09277.j.library.app.R
import com.jinxp09277.j.library.log.*
import java.text.SimpleDateFormat
import java.util.*

class JLogDemoActivity : AppCompatActivity() {
    var viewPrinter: JViewPrinter? = null

    var intArray = IntArray(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_j_log_demo)
        viewPrinter = JViewPrinter(this)
        findViewById<View>(R.id.btn_log).setOnClickListener {
            printLog()
            Log.d("test", "onCreate: ${intArray[3]}")
        }
        viewPrinter!!.viewProvider.showFloatingVIew()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            JLog.e(e.toString())
        }
        createAndSaveFile()
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


    fun createAndSaveFile() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        val sdf = SimpleDateFormat("MMddHHmm", Locale.CHINA)

        intent.apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, "JLog_${sdf.format(System.currentTimeMillis())}.txt")
        }
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val uri = data!!.data
                JLogManager.getInstance().addPrinter(JFilePrinter(this, uri))
            }
        }
    }
}