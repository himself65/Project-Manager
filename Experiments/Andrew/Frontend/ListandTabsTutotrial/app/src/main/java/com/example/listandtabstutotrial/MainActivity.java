package com.example.listandtabstutotrial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private View tabsLayout;
    private ViewPager2 pager;
    private TabsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabsorganizer);

        tabsLayout = findViewById(R.id.globalTabsLayout);
        pager = findViewById(R.id.pager);
        adapter = new TabsAdapter(this);
        pager.setAdapter(adapter);

        new TabLayoutMediator((TabLayout) tabsLayout, pager, (tab, position) -> {
            if (position == 0) tab.setText("Contacts");
            else if (position == 1) tab.setText("Favorites");
            else tab.setText("History");
        }).attach();

    }
}