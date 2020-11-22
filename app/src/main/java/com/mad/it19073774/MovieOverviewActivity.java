package com.mad.it19073774;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.it19073774.Database.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class MovieOverviewActivity extends AppCompatActivity {

    TextView name, commenthere, avgrate;
    Button submit;
    MovieListAdapter adapter;
    RatingBar mBar;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieListActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle x = intent.getExtras();
        /////////////////
        DBHandler dbHandler = new DBHandler(getApplicationContext());
        List comments = new ArrayList<>();
        comments = dbHandler.viewComments((String) x.get("Movie"));
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.mainlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieListAdapter(this, comments);
        recyclerView.setAdapter(adapter);
//////////////////////
        name = findViewById(R.id.name);

        name.setText((String) x.get("Movie"));

        mBar = (RatingBar) findViewById(R.id.rate);
        commenthere = findViewById(R.id.commenthere);
/////////////////////////
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                long newid = dbHandler.insertComments((String) x.get("Movie"),(int) mBar.getRating(), commenthere.getText().toString());

                Toast.makeText(getApplicationContext(), "Comment Added Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MovieOverviewActivity.class);
                intent.putExtra("Movie",(String) x.get("Movie"));
                startActivity(intent);
            }
        });
        //////////////////////////

        avgrate = findViewById(R.id.avgrate);
        DBHandler dbHandler2 = new DBHandler(getApplicationContext());
        Float avgrates = dbHandler2.AverageRating((String) x.get("Movie"));

        avgrate.setText(avgrates.toString());
    }
}