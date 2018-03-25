package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

@Suppress("unused")
class DelegatedStatePagerAdapter<T>(fm: FragmentManager?, private val delegatesManager: DelegatesManager<T>): FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = delegatesManager.getItem(position)

    override fun getCount(): Int = delegatesManager.getCount()

    override fun getPageTitle(position: Int): CharSequence? = delegatesManager.getPageTitle(position)

    fun setItems(items: List<T>) = delegatesManager.setItems(items)

}