package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.app.Fragment

/**
 * This class simplifies the implementation of [PagerAdapterDelegate] for use cases where our list of items
 * has a common supertype and the adapter used is determined by the specific subclass implementation
 *
 * @param I subtype handled by the adapter
 * @param T the common supertype implemented by all items
 * @property c a Java [Class] instance of type [I]
 */
abstract class SimplePagerAdapterDelegate<in I:T, T:Any>(private val c: Class<I>): PagerAdapterDelegate<T> {

    override fun isForType(item: T): Boolean {
        return c == item::class.java
    }

    @Suppress("UNCHECKED_CAST")
    override fun createFragment(items: List<T>, item: T, position: Int): Fragment {
        return createFragmentFromItem(item as I, position)
    }

    /**
     * Create a new fragment instance
     *
     * @param item the model item corresponding to the [Fragment] instance that should be created
     * @param position the position of the item in the adapter
     *
     * @return fragment instance
     */
    abstract fun createFragmentFromItem(item: I, position: Int): Fragment

}