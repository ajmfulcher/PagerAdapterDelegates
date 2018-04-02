# PagerAdapterDelegates

This library allows an Android [ViewPager](https://developer.android.com/reference/android/support/v4/view/ViewPager.html) adapter to be constructed using composition.

By delegating the creation of  ViewPager fragments to a dedicated class, our code is cleaner, and easier to reuse.

[![Build Status](https://travis-ci.org/ajmfulcher/PagerAdapterDelegates.svg?branch=master)](https://travis-ci.org/ajmfulcher/PagerAdapterDelegates)

## A Basic Example: View Pager with single page type

Each Fragment type used in a View Pager implements its own delegate. So a simple View Pager containing a single page type would require something similar to this:

```kotlin
data class ExampleModel(val title: String, val fragmentExtras: FragmentExtras)

class ExampleDelegate : PagerAdapterDelegate<ExampleModel> {

    override fun isForType(item: ExampleModel): Boolean = true

    override fun createFragment(items: List<ExampleModel>, item: ExampleModel, position: Int): Fragment {
        return ExampleFragment.newInstance(item.fragmentExtras)
    }

    override fun getPageTitle(items: List<ExampleModel>, item: ExampleModel, position: Int): CharSequence? = item.title

}
```
The ViewPager adapter then holds an instance of _DelegatesManager_, and delegates responsibility for fragment creation to it:

```kotlin
class ExampleAdapter<ExampleModel>(fm: FragmentManager?): FragmentStatePagerAdapter(fm) {

    private val delegatesManager: DelegatesManager<ExampleModel> = DelegatesManager(ExampleDelegate())

    override fun getItem(position: Int): Fragment = delegatesManager.getItem(position)

    override fun getCount(): Int = delegatesManager.getCount()

    override fun getPageTitle(position: Int): CharSequence? = delegatesManager.getPageTitle(position)

    fun setItems(items: List<ExampleModel>) = delegatesManager.setItems(items)

}
```
Each page in the list is represented by a model object  - in this example, _ExampleModel_. A list of pages is applied to the adapter using the _setItems_ method

## More complex example: View Pager with multiple page types

With multiple page types, each type requires:

- A corresponding delegate
- A way to map a particular page model object to an adapter type

A good strategy is for all of your model objects to implement a common interface, and to have a model object per page type.

```kotlin
interface ExampleModel {
    val title: String
}

data class FooModel(override val title: String, val fooId: String): ExampleModel

data class BarModel(override val title: String, val barId: String): ExampleModel
```

Then we create corresponding delegates.

```kotlin
class FooDelegate : PagerAdapterDelegate<ExampleModel> {
    
    override fun isForType(item: ExampleModel): Boolean = item is FooModel

    override fun createFragment(items: List<ExampleModel>, item: ExampleModel, position: Int): Fragment {
        if (item is FooModel) {
            return FooFragment.newInstance(item.fooId)
        } else {
            throw IllegalArgumentException("Unknown model supplied")
        }
    }

    override fun getPageTitle(items: List<ExampleModel>, item: ExampleModel, position: Int): CharSequence? = item.title

}

class BarDelegate : PagerAdapterDelegate<ExampleModel> {

    override fun isForType(item: ExampleModel): Boolean = item is BarModel

    override fun createFragment(items: List<ExampleModel>, item: ExampleModel, position: Int): Fragment {
        if (item is BarModel) {
            return BarFragment.newInstance(item.barId)
        } else {
            throw IllegalArgumentException("Unknown model supplied")
        }
    }

    override fun getPageTitle(items: List<ExampleModel>, item: ExampleModel, position: Int): CharSequence? = item.title

}
```
 Each delegate is registered with the delegates manager, with the adapter otherwise unchanged from our first example.
 
```kotlin
class ExampleAdapter<ExampleModel>(fm: FragmentManager?): FragmentStatePagerAdapter(fm) {

    private val delegatesManager: DelegatesManager<ExampleModel> = DelegatesManager(FooDelegate(), BarDelegate())

    override fun getItem(position: Int): Fragment = delegatesManager.getItem(position)

    override fun getCount(): Int = delegatesManager.getCount()

    override fun getPageTitle(position: Int): CharSequence? = delegatesManager.getPageTitle(position)

    fun setItems(items: List<ExampleModel>) = delegatesManager.setItems(items)

}
```
 
## Helper classes
 
The library also provides helper classes, which are designed to reduce the boilerplate required in your implementations.
 
By extending _SimplePagerAdapterDelegate_, the adapters used in the previous example can be greatly simplified:

```kotlin
class FooDelegate: SimplePagerAdapterDelegate<FooModel,ExampleModel>(FooModel::class.java) {

    override fun createFragmentFromItem(item: FooModel, position: Int): Fragment = FooFragment.newInstance(item.fooId)

    override fun getPageTitle(items: List<ExampleModel>, item: ExampleModel, position: Int): CharSequence? = item.title

}

class BarDelegate: SimplePagerAdapterDelegate<BarModel,ExampleModel>(BarModel::class.java) {

    override fun createFragmentFromItem(item: BarModel, position: Int): Fragment = BarFragment.newInstance(item.barId)

    override fun getPageTitle(items: List<ExampleModel>, item: ExampleModel, position: Int): CharSequence? = item.title

}
```
And _DelegatedStatePagerAdapter_ is an implementation of [FragmentStatePagerAdapter](https://developer.android.com/reference/android/support/v4/app/FragmentStatePagerAdapter.html) which passes creation tasks to _DelegatesManager_.
```kotlin
 val delegatesManager = DelegatesManager(FooDelegate(), BarDelegate())
 val adapter = DelegatedStatePagerAdapter(supportFragmentManager, delegatesManager)
```

 
## ... and a thank you!

This library is inspired by the excellent [AdapterDelegates](https://github.com/sockeqwe/AdapterDelegates) library by Hannes Dorfmann, which applies the principles of composition and delegation to Android's RecyclerView adapter. Check it out!