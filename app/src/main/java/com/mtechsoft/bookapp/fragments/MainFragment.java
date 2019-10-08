package com.mtechsoft.bookapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mtechsoft.bookapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mian, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        TextView tvBooks = view.findViewById(R.id.tvbooks);
        TextView tvPoems = view.findViewById(R.id.tvpoems);
        tvBooks.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_dashboard_to_homeFragment));
        tvPoems.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_dashboard_to_homeFragment));
    }

//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.books:
//                HomeFragment homeFragment=new HomeFragment();
//                replaceFragment(homeFragment,true,false);
//                break;
//            case R.id.poems:
//                PoemNameFragment poemNameFragment=new PoemNameFragment();
//                replaceFragment(poemNameFragment,true,false);
//                break;
//
//        }
//    }
}
