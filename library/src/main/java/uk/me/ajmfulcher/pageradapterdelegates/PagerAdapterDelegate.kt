package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.app.Fragment

/**
 * Each page type handled by [DelegatesManager] requires a corresponding implementation of this interface
 *
 * @param T the type of object handled by an implementor of this interface
 */
interface PagerAdapterDelegate<in T> {

    /**
     * Is the supplied item type handled by this delegate?
     *
     * @param item item of type [T]
     * @return do we handle this item type?
     */
    fun isForType(item: T): Boolean

    /**
     * Create a new fragment instance
     *
     * @param items a list of all model items
     * @param item the model item corresponding to the [Fragment] instance that should be created
     * @param position the position of the item in the adapter
     *
     * @return fragment instance
     */
    fun createFragment(items: List<T>, item: T, position: Int): Fragment

    /**
     * When using [android.support.v4.view.PagerTitleStrip] this method returns the title of the page
     *
     * @param items a list of all model items
     * @param item the model item corresponding to the [Fragment] instance that should be created
     * @param position the position of the item in the adapter
     *
     * @return page title
     */
    fun getPageTitle(items: List<T>, item: T, position: Int): CharSequence? = null

}