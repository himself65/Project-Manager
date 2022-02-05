package com.example.listandtabstutotrial;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabsAdapter extends FragmentStateAdapter {
    public TabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return new ContactsFragment();
        else if (position == 1) return new FavoritesFragment();
        else return new HistoryFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
