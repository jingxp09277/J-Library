package com.jinxp09277.j.library.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jingxp09277.j_ui.tab.bottom.JTabBottom
import com.jingxp09277.j_ui.tab.bottom.JTabBottomInfo
import com.jinxp09277.j.library.app.demo.log.JLogDemoActivity
import com.jinxp09277.j.library.app.demo.tab.JTabBottomDemoActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tabBottom = findViewById<JTabBottom>(R.id.tab_bottom)
        val homeInfo = JTabBottomInfo(
            "首页","fonts/iconfont.ttf",getString(R.string.if_home),
            null,"#ff656667","#ffd44949"
        )
        tabBottom.setJTabInfo(homeInfo)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_Jlog -> {
                startActivity(Intent(this, JLogDemoActivity::class.java))
            }
            R.id.tv_tab_bottom ->{
                startActivity(Intent(this, JTabBottomDemoActivity::class.java))
            }
        }
    }

}