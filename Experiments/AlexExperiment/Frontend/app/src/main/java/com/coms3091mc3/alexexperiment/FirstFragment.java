package com.coms3091mc3.alexexperiment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.coms3091mc3.alexexperiment.databinding.FragmentFirstBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ArrayAdapter<String> itemAdapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();

        TaskCardModel itemViewModel = new ViewModelProvider(requireActivity()).get(TaskCardModel.class);
        itemAdapter = new ArrayAdapter<String>(
                view.getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                itemViewModel.getItems().getValue().stream().map(TaskCard::getTitle).collect(Collectors.toList())
        );
        ListView listview = view.findViewById(R.id.item_listview);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                itemAdapter.remove(itemAdapter.getItem(i));
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        itemViewModel.getItems().observe(getViewLifecycleOwner(), new Observer<List<TaskCard>>() {
            @Override
            public void onChanged(List<TaskCard> items) {
                itemAdapter.clear();
                itemAdapter.addAll(items.stream().map(TaskCard::getTitle).collect(Collectors.toList()));
            }
        });
        listview.setAdapter(itemAdapter);

        if (context != null) {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://0824364a-c79c-4861-9550-074b210d4728.mock.pstmn.io" + "/projects";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONArray projects = response.getJSONArray("projects");
                            for (int i = 0; i < projects.length(); ++i) {
                                JSONObject object = (JSONObject) projects.get(i);
                                Gson gson = new Gson();
                                Project project = gson.fromJson(object.toString(), Project.class);
                                itemAdapter.add(project.getName());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Logger.getLogger("json").log(Level.INFO, error.toString()));

            AppController.getInstance().addToRequestQueue(request);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}