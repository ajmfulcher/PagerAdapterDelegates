package uk.me.ajmfulcher.pageradapterdelegates.app

import android.support.v4.app.Fragment
import uk.me.ajmfulcher.pageradapterdelegates.PagerAdapterDelegate
import uk.me.ajmfulcher.pageradapterdelegates.SimplePagerAdapterDelegate

class StartItemDelegate : PagerAdapterDelegate<ExampleModel> {

    override fun isForType(item: ExampleModel): Boolean = item is StartItem

    override fun createFragment(items: List<ExampleModel>, item: ExampleModel, position: Int): Fragment = StartFragment.newInstance()

    override fun getPageTitle(items: List<ExampleModel>, item: ExampleModel, position: Int): CharSequence? = item.getName()

}

class WithStringPayloadItemDelegate : SimplePagerAdapterDelegate<WithStringPayloadItem, ExampleModel>(WithStringPayloadItem::class.java) {

    override fun createFragmentFromItem(item: WithStringPayloadItem, position: Int): Fragment {
        return WithStringPayloadFragment.newInstance(item.getPayload())
    }

    override fun getPageTitle(items: List<ExampleModel>, item: ExampleModel, position: Int): CharSequence? = Integer.toString(position)

}

class EndItemDelegate : PagerAdapterDelegate<ExampleModel> {

    override fun isForType(item: ExampleModel): Boolean = item is EndItem

    override fun createFragment(items: List<ExampleModel>, item: ExampleModel, position: Int): Fragment = EndFragment.newInstance()

    override fun getPageTitle(items: List<ExampleModel>, item: ExampleModel, position: Int): CharSequence? = item.getName()

}