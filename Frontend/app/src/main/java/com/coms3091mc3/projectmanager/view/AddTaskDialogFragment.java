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
import com.coms3091mc3.projectmanager.data.Task;
import com.coms3091mc3.projectmanager.data.Team;

/**
 * The type Add task dialog fragment.
 */
public class AddTaskDialogFragment extends DialogFragment {
    /**
     * The interface Add task dialog listener.
     */
    public interface AddTaskDialogListener {
        /**
         * On dialog positive click.
         *
         * @param task the task
         */
        public void onDialogPositiveClick(Task task);
    }

    /**
     * The Listener.
     */
    AddTaskDialogFragment.AddTaskDialogListener listener;

    /**
     * Instantiates a new Add task dialog fragment.
     *
     * @param listener the listener
     */
    public AddTaskDialogFragment(AddTaskDialogFragment.AddTaskDialogListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_task, null);
        builder.setView(view)
                .setPositiveButton("New", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText text = view.findViewById(R.id.editTextTextTaskName);
                        Task task = new Task(0, text.getText().toString());
                        listener.onDialogPositiveClick(task);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddTaskDialogFragment.this.getDialog().cancel();
                    }
                }).setTitle("Add a task");
        return builder.create();
    }
}