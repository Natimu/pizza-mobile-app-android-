package com.example.cheezytown;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class clearDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Clear")
                .setMessage("Are you sure you want to clear all selections?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).clearAllFields();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create();
    }
}
