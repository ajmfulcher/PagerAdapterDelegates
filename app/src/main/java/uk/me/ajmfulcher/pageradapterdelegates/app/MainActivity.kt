package uk.me.ajmfulcher.pageradapterdelegates.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import uk.me.ajmfulcher.pageradapterdelegates.DelegatedStatePagerAdapter
import uk.me.ajmfulcher.pageradapterdelegates.DelegatesManager

class MainActivity : AppCompatActivity() {

    private val startItems = listOf(StartItem())
    private val endItems = listOf(EndItem())
    private val changingItems = mutableListOf<ExampleModel>()
    private var itemCounter = 1

    private lateinit var adapter: DelegatedStatePagerAdapter<ExampleModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = DelegatedStatePagerAdapter(
                supportFragmentManager,
                DelegatesManager(
                        StartItemDelegate(),
                        WithStringPayloadItemDelegate(),
                        EndItemDelegate()
                )

        )
        pager.adapter = adapter
        finishUpdate()

        button_add.setOnClickListener { addItem() }
        button_remove.setOnClickListener { removeItem() }
    }

    private fun addItem() {
        changingItems.add(WithStringPayloadItem("Item $itemCounter"))
        itemCounter++
        finishUpdate()
    }

    private fun removeItem() {
        if(changingItems.isNotEmpty()) {
            changingItems.removeAt(changingItems.lastIndex)
            itemCounter--
        }
        finishUpdate()
    }

    private fun finishUpdate() {
        adapter.setItems(startItems + changingItems + endItems)
        adapter.notifyDataSetChanged()
    }

}
