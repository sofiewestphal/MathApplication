package com.example.sofie.mymathapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LevelFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "com.example.sofie.mymathapplication";
    String sLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_level, container, false);

        View noviceButton = rootView.findViewById(R.id.novice_button);
        View easyButton = rootView.findViewById(R.id.easy_button);
        View mediumButton = rootView.findViewById(R.id.medium_button);
        View guruButton = rootView.findViewById(R.id.guru_button);

        // The four different level buttons all call the startGameActivity method sending in
        // a string between 1 - 4 based on the chosen level.
        noviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sLevel = 1+"";
                startGameActivity(sLevel);
            }
        });

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sLevel = 2+"";
                startGameActivity(sLevel);
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sLevel = 3+"";
                startGameActivity(sLevel);
            }
        });

        guruButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sLevel = 4+"";
                startGameActivity(sLevel);
            }
        });

        return rootView;
    }

    // The startGameActivity method starts the GameActivity and sends a message containing
    // the chosen level.
    public void startGameActivity(String sLevel){
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, sLevel);
        getActivity().startActivity(intent);
    }
}
