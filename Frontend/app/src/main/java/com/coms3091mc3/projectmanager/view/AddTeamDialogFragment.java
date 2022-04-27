package com.coms3091mc3.projectmanager.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.data.Team;

/**
 * The type Add team dialog fragment.
 */
public class AddTeamDialogFragment extends DialogFragment {
    /**
     * The interface Add team dialog listener.
     */
    public interface AddTeamDialogListener {
        /**
         * On dialog positive click.
         *
         * @param team the team
         */
        public void onDialogPositiveClick(Team team);
    }

    /**
     * The Listener.
     */
    AddTeamDialogFragment.AddTeamDialogListener listener;

    /**
     * Instantiates a new Add team dialog fragment.
     *
     * @param listener the listener
     */
    public AddTeamDialogFragment(AddTeamDialogFragment.AddTeamDialogListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_project, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_team, null);
        builder.setView(view)
                .setPositiveButton("New", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText text = view.findViewById(R.id.editTextTextPersonName);
                        Team team = new Team(0, text.getText().toString());
                        listener.onDialogPositiveClick(team);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddTeamDialogFragment.this.getDialog().cancel();
                    }
                }).setTitle("New Project");
        return builder.create();
    }
}