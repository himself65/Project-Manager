package com.coms3091mc3.projectmanager.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.databinding.FragmentSettingsBinding;
import com.coms3091mc3.projectmanager.store.SettingDataModal;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Settings fragment.
 */
public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    ImageButton button = binding.getRoot().findViewById(R.id.avatar);
                    button.setImageURI(uri);
                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.setModal(new SettingDataModal());
        View view = binding.getRoot();

        ImageButton imageButton = view.findViewById(R.id.avatar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.getLogger("avatar").log(Level.INFO, "User Click");
                mGetContent.launch("image/*");
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
