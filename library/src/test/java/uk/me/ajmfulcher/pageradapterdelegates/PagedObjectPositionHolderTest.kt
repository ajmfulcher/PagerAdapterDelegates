package uk.me.ajmfulcher.pageradapterdelegates

import android.support.v4.view.PagerAdapter
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class PagedObjectPositionHolderTest: StringSpec() {

    init {

        "When no objects have been added then nothing explodes" {
            val positionHolder = PagedObjectPositionHolder<String>()
            positionHolder.release("stuff")
            positionHolder.getItemPosition("stuff") shouldBe PagerAdapter.POSITION_NONE
            positionHolder.hold("stuff", 2) shouldBe "stuff"
        }

        "When an item is inserted then the new position is returned as unknown" {
            val positionHolder = PagedObjectPositionHolder<String>()
            val firstCall = listOf("one", "three", "four")
            val secondCall = listOf("one", "two", "three", "four")
            positionHolder.items = firstCall
            positionHolder.items = secondCall
            positionHolder.getItemPosition("two") shouldBe PagerAdapter.POSITION_NONE
        }

        "When an item is inserted then the position is returned correctly for an unchanged object" {
            val positionHolder = PagedObjectPositionHolder<String>()
            val firstCall = listOf("one", "three", "four")
            val secondCall = listOf("one", "two", "three", "four")
            positionHolder.items = firstCall
            positionHolder.hold("one", 0)
            positionHolder.hold("three", 1)
            positionHolder.hold("four", 2)
            positionHolder.items = secondCall
            positionHolder.getItemPosition("one") shouldBe 0
        }

        "When an item is inserted then the updated position is returned correctly for a moved object" {
            val positionHolder = PagedObjectPositionHolder<String>()
            val firstCall = listOf("one", "three", "four")
            val secondCall = listOf("one", "two", "three", "four")
            positionHolder.items = firstCall
            positionHolder.hold("one", 0)
            positionHolder.hold("three", 1)
            positionHolder.hold("four", 2)
            positionHolder.items = secondCall
            positionHolder.getItemPosition("three") shouldBe 2
        }

        "When an item is inserted then the position of an object outside the range of the old array should be unknown" {
            val positionHolder = PagedObjectPositionHolder<String>()
            val firstCall = listOf("one", "three", "four")
            val secondCall = listOf("one", "two", "three", "four")
            positionHolder.items = firstCall
            positionHolder.hold("one", 0)
            positionHolder.hold("three", 1)
            positionHolder.hold("four", 2)
            positionHolder.items = secondCall
            positionHolder.getItemPosition("four") shouldBe PagerAdapter.POSITION_NONE
        }

        "When an object is removed then the position of other objects should be correctly updated" {
            val positionHolder = PagedObjectPositionHolder<String>()
            val firstCall = listOf("one", "two", "three", "four")
            val secondCall = listOf("one", "two", "four")
            positionHolder.items = firstCall
            positionHolder.hold("one", 0)
            positionHolder.hold("two", 1)
            positionHolder.hold("three", 2)
            positionHolder.hold("four", 3)
            positionHolder.items = secondCall
            positionHolder.getItemPosition("one") shouldBe 0
            positionHolder.getItemPosition("two") shouldBe 1
            positionHolder.getItemPosition("three") shouldBe PagerAdapter.POSITION_NONE
            positionHolder.getItemPosition("four") shouldBe 2
        }

        "When an item is released then we no longer track its position" {
            val positionHolder = PagedObjectPositionHolder<String>()
            val firstCall = listOf("one", "two")
            val secondCall = listOf("one", "two")
            positionHolder.items = firstCall
            positionHolder.hold("one", 0)
            positionHolder.hold("two", 1)
            positionHolder.items = secondCall
            positionHolder.getItemPosition("two") shouldBe 1
            positionHolder.release("two")
            positionHolder.getItemPosition("two") shouldBe PagerAdapter.POSITION_NONE
        }

    }

}