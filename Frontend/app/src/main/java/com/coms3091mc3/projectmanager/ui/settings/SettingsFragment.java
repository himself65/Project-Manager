package com.coms3091mc3.projectmanager.ui.settings;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.databinding.FragmentSettingsBinding;
import com.coms3091mc3.projectmanager.store.SettingDataModal;

/**
 * The type Settings fragment.
 */
public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.setModal(new SettingDataModal());
        View view = binding.getRoot();
        SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.themeSwitch);
        // set default value
        int isNightTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (isNightTheme) {
            case Configuration.UI_MODE_NIGHT_NO:
                switchCompat.setChecked(false);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                switchCompat.setChecked(true);
                break;
        }
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isNightTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (isNightTheme) {
                    case Configuration.UI_MODE_NIGHT_NO:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
