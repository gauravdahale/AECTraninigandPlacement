package com.gtech.aectnp.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gtech.aectnp.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {
    private val mFragmentList: ArrayList<Fragment> = ArrayList()
    private val mFragmentTitleList: ArrayList<String> = ArrayList()
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return mFragmentList[position];
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList.get(position)
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return mFragmentList.size
    }
    fun addFrag(
        fragment: Fragment?,
        title: String?
    ) {
        mFragmentList.add(fragment!!)
        mFragmentTitleList.add(title!!)
    }

}