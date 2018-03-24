package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.app.Fragment
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.StringSpec

class SimplePagerAdapterDelegateTest: StringSpec() {

    init {

        "When type matches then isForType is true" {
            val delegate = CapturingSimplePagerAdapterDelegate()
            delegate.isForType("Stuff") shouldBe true
        }

        "When type does not match then isForType is false" {
            val delegate = CapturingSimplePagerAdapterDelegate()
            delegate.isForType(StringBuffer()) shouldBe false
        }

        "When type matches then createFragmentFromItem is called" {
            val delegate = CapturingSimplePagerAdapterDelegate()
            val item = "Stuff"
            delegate.createFragment(listOf(item),item, 0)
            delegate.createFragmentFromItemCalled shouldBe true
        }

        "When type does not match then createFragmentFromItem is called and an exception is thrown" {
            val delegate = CapturingSimplePagerAdapterDelegate()
            shouldThrow<ClassCastException> {
                val item = StringBuffer()
                delegate.createFragment(listOf(item), item, 0)
            }
            delegate.createFragmentFromItemCalled shouldBe false
        }
    }

}

class CapturingSimplePagerAdapterDelegate: SimplePagerAdapterDelegate<String, CharSequence>(String::class.java) {

    var createFragmentFromItemCalled = false

    override fun createFragmentFromItem(item: String, position: Int): Fragment {
        createFragmentFromItemCalled = true
        return Fragment()
    }

}