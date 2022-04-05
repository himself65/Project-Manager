package com.coms3091mc3.projectmanager.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coms3091mc3.projectmanager.databinding.FragmentTeamBinding;
import com.coms3091mc3.projectmanager.store.TeamDataModel;

public class TeamFragment extends Fragment {
    FragmentTeamBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTeamBinding.inflate(inflater, container, false);
        binding.setModal(new TeamDataModel());
        return binding.getRoot();
    }
}
