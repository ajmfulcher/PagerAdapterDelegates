package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.view.PagerAdapter
import java.util.*
import kotlin.properties.Delegates

/**
 * This class is responsible for tracking the position of items in a PagerAdapter.
 * This allows dynamic updating of the ViewPager items
 */
class PagedObjectPositionHolder<T> {

    private var oldLength = 0
    private val objectMap = WeakHashMap<Any, T>()

    /**
     * @property items should be updated whenever the contents of the parent adapter changes
     */
    var items by Delegates.observable(emptyList<T>()) {
        _, oldItems, _ ->  oldLength = oldItems.size
    }

    /**
     * Call this from the [PagerAdapter] instantiateItem() method
     * @param item the item returned by a call to super.instantiateItem() on the parent adapter
     * @param position index of the item that's been added
     * @return the item passed in.
     */
    fun hold(item: Any, position: Int) : Any {
        items.elementAtOrNull(position)?.let {
            objectMap[item] = it
        }
        return item
    }

    /**
     * Call this from the [PagerAdapter] destroyItem() method
     * @param item the item being destroyed
     */
    fun release(item: Any) {
        objectMap.remove(item)
    }

    /**
     * Delegate calls to [PagerAdapter] getItemPosition() to this method
     * @param the item to resolve position for
     * @return the position of the item, or [PagerAdapter.POSITION_NONE] if it's unknown
     */
    fun getItemPosition(item: Any): Int {
        objectMap[item].let {
            val itemIndex = items.indexOf(it)
            if(itemIndex != -1 && itemIndex < oldLength) {
                return itemIndex
            }
        }
        return PagerAdapter.POSITION_NONE
    }

}