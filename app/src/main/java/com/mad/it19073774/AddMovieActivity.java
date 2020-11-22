package com.mad.it19073774;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mad.it19073774.Database.DBHandler;

public class AddMovieActivity extends AppCompatActivity {

    EditText moviename, movieyear;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        moviename = findViewById(R.id.name);
        movieyear = findViewById(R.id.year);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addmovie (View view) {
        DBHandler dbHandler = new DBHandler(getApplicationContext());
        long newID = dbHandler.addMovie(moviename.getText().toString(), Integer.parseInt(movieyear.getText().toString()));
        Toast.makeText(getApplicationContext(), "Movie Added Successfully : " + newID, Toast.LENGTH_SHORT).show();
    }
}