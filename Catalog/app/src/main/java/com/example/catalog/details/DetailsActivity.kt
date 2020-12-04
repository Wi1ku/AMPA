package com.example.catalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator


val fragments = listOf(Pair("Description", detail_main()), Pair("Album List",detail_albums()), Pair("Gallery", detail_gallery()))

class DetailsActivity : AppCompatActivity() {
    var clickedElementId: Int = -1
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = intent.getIntExtra("id", -1)
        clickedElementId = i
        setContentView(R.layout.activity_details)

        viewPager = findViewById(R.id.pager)

        val pagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(findViewById(R.id.tab_layout), viewPager,
            TabLayoutMediator.OnConfigureTabCallback { tab, position -> // Styling each tab here
                tab.text = fragments[position].first
            }).attach()
    }


    private inner class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position].second
    }

}