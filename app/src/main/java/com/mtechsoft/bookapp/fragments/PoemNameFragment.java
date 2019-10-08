package com.mtechsoft.bookapp.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mtechsoft.bookapp.activities.MainActivity;
import com.mtechsoft.bookapp.R;
import com.mtechsoft.bookapp.adapters.PoemAdapter;
import com.mtechsoft.bookapp.models.Poem;

import java.util.ArrayList;
public class PoemNameFragment extends BaseFragment implements PoemAdapter.Callback {
    private RecyclerView recyclerView;
    private PoemAdapter pAdapter;
    private ArrayList<Poem> poems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_poem_name, container, false);

        init(v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
    }

    private void init(View v) {
        recyclerView = v.findViewById(R.id.rv_poemName);

    }

    private void setPoemsName() {
        poems = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Poem poem = new Poem();
            poem.setPoemName("Poem " + i);
            poem.setChapterDetail(getResources().getString(R.string.story));
            poem.setPages(10);
            poems.add(poem);
        }
    }

    private void initAdapter() {

        setPoemsName();
        pAdapter = new PoemAdapter(getActivity(),poems,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(pAdapter);
    }


    @Override
    public void onItemClick(int pos) {
        ChapterDetailFragment fragment = new ChapterDetailFragment();
      //  ((MainActivity) getActivity()).replaceFragment(fragment, true, true);


    }
}
