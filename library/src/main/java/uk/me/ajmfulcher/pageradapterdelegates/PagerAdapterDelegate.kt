package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.app.Fragment

interface PagerAdapterDelegate<T> {

    fun isForType(item: T): Boolean

    fun createFragment(items: List<T>, item: T, position: Int): Fragment

}