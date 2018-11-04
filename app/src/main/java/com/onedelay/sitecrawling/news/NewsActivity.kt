package com.onedelay.sitecrawling.news

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.onedelay.sitecrawling.Constants
import com.onedelay.sitecrawling.R
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private lateinit var sharedPref: SharedPreferences

    private lateinit var portal: String

    companion object {
        val daumCategories = listOf("뉴스", "연예", "스포츠")
        val naverCategories = listOf("정치", "경제", "사회", "생활/문화", "세계", "IT/과학")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        portal = sharedPref.getString(Constants.SELECTED_PORTAL, Constants.NAVER)

        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        mSectionsPagerAdapter?.setCategory(when (portal) {
            Constants.DAUM -> daumCategories
            else -> naverCategories
        })
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        tabs.setupWithViewPager(container, true)
    }

    // SharedPreference 내용이 변경될 때마다 호출되는 리스너
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        mSectionsPagerAdapter!!.setCategory(when (p0?.getString(p1, Constants.NAVER)) {
            Constants.DAUM -> daumCategories
            else -> naverCategories
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        // 어떤 portal 의 뉴스를 보여줄 지 옵션메뉴에서 설정
        portal = when (id) {
            R.id.daum -> Constants.DAUM
            else -> Constants.NAVER
        }

        // shared preference 내용 변경
        with(sharedPref.edit()) {
            putString(Constants.SELECTED_PORTAL, portal)
            apply()
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        sharedPref.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        sharedPref.unregisterOnSharedPreferenceChangeListener(this)
    }
}