package com.example.sofie.mymathapplication;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.Context.MODE_PRIVATE;
import static com.example.sofie.mymathapplication.GameActivity.PREF_RESTORE;

public class ExitFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_exit, container, false);

        View saveButton = rootView.findViewById(R.id.save_button);
        View exitButton = rootView.findViewById(R.id.exit_button);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitApp();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Remove the key - value pairs storing the current game information.
                SharedPreferences sharedpref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
                sharedpref.edit().remove("SavedLevel").commit();
                sharedpref.edit().remove("SavedNumberOfQuestions").commit();
                exitApp();
            }
        });

        return rootView;
    }

    private void exitApp(){
        System.exit(0);
    }
}
