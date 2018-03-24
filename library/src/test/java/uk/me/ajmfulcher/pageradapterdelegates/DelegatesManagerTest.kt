package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.app.Fragment

import io.kotlintest.matchers.beInstanceOf
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.StringSpec

class DelegatesManagerTest: StringSpec() {

    init {

        "When no items are added then getCount is zero" {
            val delegatesManager = DelegatesManager<String>()
            delegatesManager.getCount() shouldBe 0
        }

        "When items are added then getCount should be equal to the number added" {
            val delegatesManager = DelegatesManager<String>()
            delegatesManager.setItems(listOf("one", "two", "three", "four", "five"))
            delegatesManager.getCount() shouldBe 5
        }

        "When we request a fragment then the supplied item type is checked" {
            val delegatesManager = DelegatesManager<String>()
            val delegate = CapturingPagerAdapterDelegate()
            delegatesManager.addDelegate(delegate)
            delegatesManager.setItems(listOf("one", "two", "three", "four", "five"))
            delegatesManager.getItem(2)
            delegate.isForTypeCalled shouldBe true
        }

        "When we request a fragment and the item matches then the fragment creator is called" {
            val delegatesManager = DelegatesManager<String>()
            val delegate = CapturingPagerAdapterDelegate()
            delegatesManager.addDelegate(delegate)
            delegatesManager.setItems(listOf("one", "two", "three", "four", "five"))
            delegatesManager.getItem(2)
            delegate.isCreateFragmentCalled shouldBe true
        }

        "When we request an item by position then the correct item is supplied to the delegate" {
            val delegatesManager = DelegatesManager<String>()
            val delegate = CapturingPagerAdapterDelegate()
            delegatesManager.addDelegate(delegate)
            delegatesManager.setItems(listOf("one", "two", "three", "four", "five"))
            delegatesManager.getItem(2)
            delegate.typeItemSupplied shouldBe "three"
            delegate.createItemSupplied shouldBe "three"
        }

        "When no matching delegate is found then an IllegalArgumentException is thrown" {
            val delegatesManager = DelegatesManager<String>()
            delegatesManager.addDelegate(NeverMatchesDelegate())
            delegatesManager.setItems(listOf("one", "two", "three", "four", "five"))
            shouldThrow<IllegalArgumentException> {
                delegatesManager.getItem(2)
            }
        }

        "A new fragment is returned that matches the requested item" {
            val delegatesManager = DelegatesManager<String>()
            delegatesManager.addDelegate(MatchedDelegate())
            delegatesManager.addDelegate(UnmatchedDelegate())
            delegatesManager.setItems(listOf("unmatched", "matched", "unmatched", "unmatched", "matched"))
            delegatesManager.getItem(0) shouldBe beInstanceOf(UnmatchedFragment::class)
            delegatesManager.getItem(1) shouldBe beInstanceOf(MatchedFragment::class)
            delegatesManager.getItem(2) shouldBe beInstanceOf(UnmatchedFragment::class)
            delegatesManager.getItem(3) shouldBe beInstanceOf(UnmatchedFragment::class)
            delegatesManager.getItem(4) shouldBe beInstanceOf(MatchedFragment::class)
        }

        "Delegates can be added through the constructor" {
            val delegatesManager = DelegatesManager(MatchedDelegate(), UnmatchedDelegate())
            delegatesManager.setItems(listOf("unmatched", "matched"))
            delegatesManager.getItem(0) shouldBe beInstanceOf(UnmatchedFragment::class)
            delegatesManager.getItem(1) shouldBe beInstanceOf(MatchedFragment::class)
        }
    }

}

class CapturingPagerAdapterDelegate : PagerAdapterDelegate<String> {

    var isForTypeCalled = false
    var typeItemSupplied: String? = null

    var isCreateFragmentCalled = false
    var createItemSupplied: String? = null

    override fun isForType(item: String): Boolean {
        isForTypeCalled = true
        typeItemSupplied = item
        return true
    }

    override fun createFragment(item: String): Fragment {
        isCreateFragmentCalled = true
        createItemSupplied = item
        return Fragment()
    }

}

class NeverMatchesDelegate: PagerAdapterDelegate<String> {

    override fun isForType(item: String): Boolean {
        return false
    }

    override fun createFragment(item: String): Fragment {
        throw RuntimeException("This should not be reached")
    }

}

class MatchedDelegate: PagerAdapterDelegate<String> {

    override fun isForType(item: String): Boolean = item == "matched"

    override fun createFragment(item: String): Fragment {
        return MatchedFragment()
    }

}

class UnmatchedDelegate: PagerAdapterDelegate<String> {

    override fun isForType(item: String): Boolean = item != "matched"

    override fun createFragment(item: String): Fragment {
        return UnmatchedFragment()
    }

}

class MatchedFragment: Fragment() {

    override fun toString(): String = "MatchedFragment"

}

class UnmatchedFragment: Fragment() {

    override fun toString(): String = "UnmatchedFragment"

}