package uk.me.ajmfulcher.pageradapterdelegates.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import uk.me.ajmfulcher.pageradapterdelegates.DelegatedStatePagerAdapter
import uk.me.ajmfulcher.pageradapterdelegates.DelegatesManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = DelegatedStatePagerAdapter(
                supportFragmentManager,
                DelegatesManager(
                        StartItemDelegate(),
                        WithStringPayloadItemDelegate(),
                        EndItemDelegate()
                )

        )
        val items = listOf(
                StartItem(),
                WithStringPayloadItem("An item"),
                WithStringPayloadItem("Another item"),
                EndItem()
        )
        adapter.setItems(items)
        pager.adapter = adapter
    }

}
