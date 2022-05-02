package com.coms3091mc3.projectmanager.ui.settings;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.databinding.FragmentSettingsBinding;
import com.coms3091mc3.projectmanager.store.SettingDataModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
                    byte[] bytes = getBytesArrayFromURI(uri);
                    JSONArray array = new JSONArray();
                    try {
                        for (byte b : bytes) {
                            array.put(b);
                        }
                        JSONObject data = new JSONObject();
                        data.put("image", array);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, "someAPI", data,
                                response -> {
                                    // todo
                                },
                                error -> {
                                    // todo
                                }
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    public byte[] getBytesArrayFromURI(Uri uri) {
        try {
            ContentResolver resolver = getContext().getContentResolver();
            InputStream inputStream = resolver.openInputStream(uri);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            return byteBuffer.toByteArray();

        } catch (Exception e) {
            Log.d("exception", "Oops! Something went wrong.");
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
