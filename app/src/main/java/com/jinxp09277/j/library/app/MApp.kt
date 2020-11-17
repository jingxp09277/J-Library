package com.jinxp09277.j.library.app

import android.app.Application
import android.content.Intent
import com.google.gson.Gson
import com.jinxp09277.j.library.log.JConsolePrinter
import com.jinxp09277.j.library.log.JFilePrinter
import com.jinxp09277.j.library.log.JLogConfig
import com.jinxp09277.j.library.log.JLogConfig.JsonParser
import com.jinxp09277.j.library.log.JLogManager

/**
 * Created by LWB on 2020/10/27
 */

class MApp : Application() {
    override fun onCreate() {
        super.onCreate()
        JLogManager.init(object : JLogConfig() {
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }

            override fun getGlobalTag(): String {
                return "MApp"
            }

            override fun enable(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        },JConsolePrinter())
    }
}