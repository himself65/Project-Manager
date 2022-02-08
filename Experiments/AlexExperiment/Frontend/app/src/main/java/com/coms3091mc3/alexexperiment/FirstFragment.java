package com.coms3091mc3.alexexperiment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.coms3091mc3.alexexperiment.databinding.FragmentFirstBinding;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ItemViewModel itemViewModel;
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
        itemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        itemAdapter = new ArrayAdapter<String>(
                view.getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                itemViewModel.getItems().getValue().stream().map(Item::getTitle).collect(Collectors.toList())
        );
        ListView listview = view.findViewById(R.id.item_listview);
        itemViewModel.getItems().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                itemAdapter.clear();
                itemAdapter.addAll(items.stream().map(Item::getTitle).collect(Collectors.toList()));
            }
        });
        listview.setAdapter(itemAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}