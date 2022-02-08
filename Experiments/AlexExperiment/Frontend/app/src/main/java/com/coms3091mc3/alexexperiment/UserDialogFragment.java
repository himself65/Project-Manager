package com.coms3091mc3.alexexperiment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.coms3091mc3.alexexperiment.databinding.FragmentUserDialogBinding;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDialogFragment extends DialogFragment {
    private FragmentUserDialogBinding userDialogBinding;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(TaskCard item);

        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        userDialogBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_user_dialog, null, false);
        userDialogBinding.setTaskCard(new TaskCard(""));

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(userDialogBinding.getRoot())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(userDialogBinding.getTaskCard());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        userDialogBinding = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }
}