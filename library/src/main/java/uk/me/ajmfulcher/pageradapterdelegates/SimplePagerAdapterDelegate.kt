package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.app.Fragment

abstract class SimplePagerAdapterDelegate<in I:T, T:Any>(private val c: Class<I>): PagerAdapterDelegate<T> {

    override fun isForType(item: T): Boolean {
        return c == item::class.java
    }

    @Suppress("UNCHECKED_CAST")
    override fun createFragment(items: List<T>, item: T, position: Int): Fragment {
        return createFragmentFromItem(item as I, position)
    }

    abstract fun createFragmentFromItem(item: I, position: Int): Fragment

}