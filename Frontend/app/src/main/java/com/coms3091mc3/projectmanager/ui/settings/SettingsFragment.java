package com.coms3091mc3.projectmanager.ui.settings;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.app.AppController;
import com.coms3091mc3.projectmanager.data.User;
import com.coms3091mc3.projectmanager.databinding.FragmentSettingsBinding;
import com.coms3091mc3.projectmanager.utils.Const;

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
                    if (uri == null) {
                        return;
                    }
                    ImageButton button = binding.getRoot().findViewById(R.id.avatar);
                    byte[] bytes = getBytesArrayFromURI(uri);
                    JSONArray array = new JSONArray();
                    try {
                        for (byte b : bytes) {
                            array.put(b);
                        }
                        JSONObject data = new JSONObject();
                        data.put("image", array);
                        data.put("id", Const.user.getUserID());
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, Const.API_SERVER + "/user/" + Const.user.getUserID() + "/image", data,
                                response -> {
                                    // todo
                                },
                                error -> {
                                    // todo
                                }
                        );
                        AppController.getInstance().addToRequestQueue(request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    button.setImageURI(uri);
                }
            });

    public JsonObjectRequest fetchAvatar() {
        User profile = new User(Const.user.getUserID(), Const.user.getUsername(), Const.user.getFullname());
        if(getArguments() != null) { //being called from project fragment
            profile = new User(getArguments().getInt("userID"),
                    getArguments().getString("username"),
                    getArguments().getString("fullname"));
            //Reset name & fullname
            TextView textView = binding.getRoot().findViewById(R.id.username);
            textView.setText(profile.getUsername());

            TextView fullName = binding.getRoot().findViewById(R.id.fullname);
            fullName.setText(profile.getFullname());
        }

        return new JsonObjectRequest(Request.Method.GET, Const.API_SERVER + "/user/" + profile.getUserID() + "/image", null,
                response -> {
                    try {
                        String message = response.getString("image");
                        String[] stringArray = message.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                        byte[] byteArray = new byte[stringArray.length];

                        for (int i = 0; i < stringArray.length; i++) {
                            byteArray[i] = (byte) Integer.parseInt(stringArray[i]);
                        }
                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        ImageButton button = binding.getRoot().findViewById(R.id.avatar);
                        button.setImageBitmap(bitmap);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                }
        );
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        TextView textView = view.findViewById(R.id.username);
        textView.setText(Const.user.getUsername());

        TextView fullName = view.findViewById(R.id.fullname);
        fullName.setText(Const.user.getFullname());

        ImageButton imageButton = view.findViewById(R.id.avatar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.getLogger("avatar").log(Level.INFO, "User Click");
                mGetContent.launch("image/*");
            }
        });
        JsonObjectRequest request = fetchAvatar();
        AppController.getInstance().addToRequestQueue(request);
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
