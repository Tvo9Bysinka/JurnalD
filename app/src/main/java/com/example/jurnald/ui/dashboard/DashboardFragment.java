package com.example.jurnald.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.jurnald.Dobavlenie;
import com.example.jurnald.Dobavlenie_Jurnal;
import com.example.jurnald.Dobavlenie_Predmeta;
import com.example.jurnald.Dobavlenie_Prepodavatel9;
import com.example.jurnald.R;
import com.example.jurnald.Smotret_Jurnal;
import com.example.jurnald.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView imv = (ImageView) binding.imageView2;
        ImageView imv1 = (ImageView) binding.imageView4;
        ImageView imv2 = (ImageView) binding.imageView3;
        ImageView imv3 = (ImageView) binding.imageView5;
        imv.setImageResource(R.drawable.kisspng_professor_x_cartoon_animation_character_professor_5ac1af9e4ff6c8_9026922915226428463275);
        imv1.setImageResource(R.drawable.predmet);
        imv2.setImageResource(R.drawable.fngs);
        imv3.setImageResource(R.drawable.ic_jurnal);



        Button btn = (Button) binding.button;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Dobavlenie_Predmeta.class);
                startActivity(intent);
            }
        });
        Button btn1 = (Button) binding.button2;
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Dobavlenie.class);
                startActivity(intent);
            }
        });
        Button btn2 = (Button) binding.button3;
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Dobavlenie_Prepodavatel9.class);
                startActivity(intent);
            }
        });
        Button btn3 = (Button) binding.button12;
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Dobavlenie_Jurnal.class);
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