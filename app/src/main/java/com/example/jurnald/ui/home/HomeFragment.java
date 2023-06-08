package com.example.jurnald.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jurnald.Dobavlenie_Jurnal;
import com.example.jurnald.Dobavlenie_Predmeta;
import com.example.jurnald.R;
import com.example.jurnald.Smotret_Jurnal;
import com.example.jurnald.Smotret_Propyski;
import com.example.jurnald.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView imv = (ImageView) binding.imageView7;
        ImageView imv1 = (ImageView) binding.imageView8;
        imv.setImageResource(R.drawable.pngwing_com__1_);
        imv1.setImageResource(R.drawable.pngwing_com__2_);



        Button btn = (Button) binding.button4;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Smotret_Propyski.class);
                startActivity(intent);
            }
        });


        Button btn1 = (Button) binding.button5;

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Smotret_Jurnal.class);
                startActivity(intent);
            }
        });
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}