package uk.me.ajmfulcher.pageradapterdelegates.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import uk.me.ajmfulcher.pageradapterdelegates.DelegatedStatePagerAdapter;
import uk.me.ajmfulcher.pageradapterdelegates.DelegatesManager;

@SuppressLint("Registered")
public class ExampleActivityJava extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DelegatedStatePagerAdapter<ExampleModel> adapter = new DelegatedStatePagerAdapter<>(
                getSupportFragmentManager(),
                new DelegatesManager<>(
                        new StartItemDelegate(),
                        new WithStringPayloadItemDelegate(),
                        new EndItemDelegate()
                )
        );

        List<ExampleModel> items = new ArrayList<>();
        items.add(new StartItem());
        items.add(new WithStringPayloadItem("An item"));
        items.add(new WithStringPayloadItem("An item"));
        items.add(new EndItem());
        adapter.setItems(items);

        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(adapter);
    }

}
