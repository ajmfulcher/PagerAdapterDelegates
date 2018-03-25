package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.app.Fragment

class DelegatesManager<T>(vararg delegates: PagerAdapterDelegate<T>) {

    private var delegates = mutableListOf<PagerAdapterDelegate<T>>()

    private var items: List<T> = emptyList()

    init {
        delegates.forEach(this::addDelegate)
    }

    fun setItems(items: List<T>) {
        this.items = items
    }

    fun getCount(): Int = items.size

    fun getItem(position: Int): Fragment {
        val item = items[position]
        return getDelegateForPosition(item).createFragment(items, item, position)
    }

    fun getPageTitle(position: Int): CharSequence? {
        val item = items[position]
        return getDelegateForPosition(item).getPageTitle(items, item, position)
    }

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