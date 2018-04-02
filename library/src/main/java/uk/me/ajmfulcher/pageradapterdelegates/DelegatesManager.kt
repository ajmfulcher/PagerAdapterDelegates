package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.app.Fragment

/**
 * This class has responsibility for page operations delegated to it by an implementation of the
 * View Pager adapter
 *
 * @param T the model type used to represent adapter pages
 * @property delegates a list of [PagerAdapterDelegate] implementations that handle operations for a particular page type
 */
class DelegatesManager<T>(vararg delegates: PagerAdapterDelegate<T>) {

    private var delegates = mutableListOf<PagerAdapterDelegate<T>>()

    private var items: List<T> = emptyList()

    init {
        delegates.forEach(this::addDelegate)
    }

    /**
     * Populates the delegates manager with data
     *
     * @property items list of items of type [T] representing the pages managed by us
     */
    fun setItems(items: List<T>) {
        this.items = items
    }

    /**
     * The number of pages in the View Pager
     *
     * The adapter method getCount() should delegate to this method
     */
    fun getCount(): Int = items.size

    /**
     * Get a [Fragment] instance
     * @param position the position of the fragment in the adapter
     *
     * The adapter method getItem(position: Int) should delegate to this method
     */
    fun getItem(position: Int): Fragment {
        val item = items[position]
        return getDelegateForPosition(item).createFragment(items, item, position)
    }

    /**
     * Get the page title
     * @param position the position of the page in the adapter
     *
     * The adapter method getPageTitle(position: Int) should delegate to this method
     */
    fun getPageTitle(position: Int): CharSequence? {
        val item = items[position]
        return getDelegateForPosition(item).getPageTitle(items, item, position)
    }

    /**
     * Add an adapter delegate. This method can be used as an alternative (or in addition) to specifying
     * adapter delegates in the constructor
     */
    fun addDelegate(delegate: PagerAdapterDelegate<T>) {
        delegates.add(delegate)
    }

    private fun getDelegateForPosition(item: T): PagerAdapterDelegate<T> {
        delegates.forEach {
            if (it.isForType(item)) return it
        }
        throw IllegalArgumentException("No matching delegate found for item " + item.toString())
    }

}