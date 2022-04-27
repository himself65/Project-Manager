package com.coms3091mc3.projectmanager.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.data.Project;

import java.util.Date;

public class AddProjectDialogFragment extends DialogFragment {
    public interface AddProjectDialogListener {
        public void onDialogPositiveClick(Project projectName);
    }

    AddProjectDialogListener listener;

    public AddProjectDialogFragment(AddProjectDialogListener listener) {
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
        View view = inflater.inflate(R.layout.fragment_add_project, null);
        builder.setView(view)
                .setPositiveButton("New", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText text = view.findViewById(R.id.edit_projectName);
                        Date date = new Date();
                        Project project = new Project(0, text.getText().toString(), date.toString());
                        listener.onDialogPositiveClick(project);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddProjectDialogFragment.this.getDialog().cancel();
                    }
                }).setTitle("New Project");
        return builder.create();
    }
}
