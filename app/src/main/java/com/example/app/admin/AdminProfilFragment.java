package com.example.app.admin;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.app.R;

public class AdminProfilFragment extends Fragment {

    Button creeazaTort;
    ConstraintLayout backimg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_profil, null);
        getActivity().setTitle("Creeaza tort");

        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.imagine6), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.imagine2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.imagine1), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.imagine4), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.imagine5), 3000);

        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setExitFadeDuration(1600);

        backimg = v.findViewById(R.id.back1);
        backimg.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        creeazaTort = (Button) v.findViewById(R.id.creaza_tort);

        creeazaTort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AdminCreeazaTort.class));
            }
        });

        return v;
    }
}
