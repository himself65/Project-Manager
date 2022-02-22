package com.coms3091mc3.projectmanager.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coms3091mc3.projectmanager.AppController;
import com.coms3091mc3.projectmanager.BuildConfig;
import com.coms3091mc3.projectmanager.databinding.FragmentDashboardBinding;
import com.coms3091mc3.projectmanager.store.User;

import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        Context context = getContext();
        if (context != null) {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = BuildConfig.apiUrl + "/projects";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> Logger.getLogger("json").log(Level.INFO, response.toString()),
                    error -> Logger.getLogger("json").log(Level.INFO, error.toString()));

            AppController.getInstance().addToRequestQueue(request);
        }
        binding.setUser(new User("unknown"));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}