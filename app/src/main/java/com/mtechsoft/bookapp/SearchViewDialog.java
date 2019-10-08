package com.mtechsoft.bookapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.mtechsoft.bookapp.activities.MainActivity;

public class SearchViewDialog extends DialogFragment {

    public static SearchViewDialog newInstance(int title) {
        SearchViewDialog frag = new SearchViewDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setView(R.layout.search_view_dialog_box)
                .create();
    }
}