package com.jinxp09277.j.library.app.demo.tab

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.jingxp09277.j_ui.tab.bottom.JTabBottomInfo
import com.jingxp09277.j_ui.tab.bottom.JTabBottomLayout
import com.jinxp09277.j.library.app.R
import com.jinxp09277.j.library.util.JDisplayUtil

class JTabBottomDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_tab_bottom_demo)
        initTabBottom()
    }

    private fun initTabBottom() {
        val jTabBottomLayout:JTabBottomLayout = findViewById(R.id.jtablayout)
        jTabBottomLayout.setTabAlpha(0.85f)
        val bottomInfoList:MutableList<JTabBottomInfo<*>> = ArrayList()

        val infoHome = JTabBottomInfo(
            "首页","fonts/iconfont.ttf",getString(R.string.if_home),
            null,"#ff656667","#ffd44949"
        )
        val infoFavorite = JTabBottomInfo(
            "收藏","fonts/iconfont.ttf",getString(R.string.if_favorite),
            null,"#ff656667","#ffd44949"
        )
//        val infoCategory = JTabBottomInfo(
//            "分类","fonts/iconfont.ttf",getString(R.string.if_category),
//            null,"#ff656667","#ffd44949"
//        )

        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.logo,null)
        val infoCategory = JTabBottomInfo<String>(
            "分类",bitmap,bitmap
        )

        val infoRecommend = JTabBottomInfo(
            "推荐","fonts/iconfont.ttf",getString(R.string.if_recommend),
            null,"#ff656667","#ffd44949"
        )
        val infoProfile = JTabBottomInfo(
            "我的","fonts/iconfont.ttf",getString(R.string.if_profile),
            null,"#ff656667","#ffd44949"
        )

        bottomInfoList.add(infoHome)
        bottomInfoList.add(infoFavorite)
        bottomInfoList.add(infoCategory)
        bottomInfoList.add(infoRecommend)
        bottomInfoList.add(infoProfile)

        jTabBottomLayout.inflateInfo(bottomInfoList)
        jTabBottomLayout.addTabSelectedChangeListener{_,_,nextInfo->
            Toast.makeText(this@JTabBottomDemoActivity,nextInfo.name,Toast.LENGTH_SHORT).show()
        }
        jTabBottomLayout.defaultSelected(infoHome)
        //改变某个tab的高度
        val tabBottom = jTabBottomLayout.findTab(bottomInfoList[2])
        tabBottom?.apply { resetHeight(JDisplayUtil.dp2px(66f,resources)) }
    }


}