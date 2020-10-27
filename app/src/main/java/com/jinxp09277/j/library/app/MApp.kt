package com.jinxp09277.j.library.app

import android.app.Application
import com.jinxp09277.j.library.log.JLogConfig
import com.jinxp09277.j.library.log.JLogManager

/**
 * Created by LWB on 2020/10/27
 */

class MApp : Application() {
    override fun onCreate() {
        super.onCreate()
        JLogManager.init(object : JLogConfig() {
            override fun getGlobalTag(): String {
                return "MApp"
            }

            override fun enable(): Boolean {
                return true
            }
        })
    }
}