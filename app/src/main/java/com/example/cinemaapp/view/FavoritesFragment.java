package com.example.cinemaapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaapp.R;
import com.example.cinemaapp.adapters.FavoriteFilmAdapter;
import com.example.cinemaapp.repository.Repository;


public class FavoritesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        final Fragment pointerSaver = this;

        //Add recyclerView
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view_favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        final FavoriteFilmAdapter adapter = new FavoriteFilmAdapter(this);
        recyclerView.setAdapter(adapter);


        if (Repository.favoriteList.isEmpty()) {
            final TextView header = view.findViewById(R.id.no_favorites_message_header);
            header.setVisibility(View.VISIBLE);
            final TextView body = view.findViewById(R.id.no_favorites_message_body);
            body.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
