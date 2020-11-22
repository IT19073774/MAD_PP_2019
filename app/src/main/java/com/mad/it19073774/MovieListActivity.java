package com.mad.it19073774;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.mad.it19073774.Database.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    MovieListAdapter adapter;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        DBHandler dbHandler = new DBHandler(getApplicationContext());
        List movies = new ArrayList<>();
        movies = dbHandler.viewMovies();
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.movielist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieListAdapter(this, movies);
        recyclerView.setAdapter(adapter);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}