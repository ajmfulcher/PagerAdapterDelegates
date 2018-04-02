package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Implementation of [FragmentStatePagerAdapter] that delegates page creation operations to [DelegatesManager]
 *
 * @param T the supertype of page model objects held by the adapter
 * @param fm the [FragmentManager]
 * @property delegatesManager an instance of [DelegatesManager] that is wrapped by this class
 */
@Suppress("unused")
class DelegatedStatePagerAdapter<T>(fm: FragmentManager?, private val delegatesManager: DelegatesManager<T>): FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = delegatesManager.getItem(position)

    override fun getCount(): Int = delegatesManager.getCount()

    override fun getPageTitle(position: Int): CharSequence? = delegatesManager.getPageTitle(position)

    /**
     * Populates the adapter with data
     *
     * @property items list of items of type [T] representing the pages held by the adapter
     */
    fun setItems(items: List<T>) = delegatesManager.setItems(items)

}