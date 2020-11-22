package com.mad.it19073774;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mad.it19073774.Database.DBHandler;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                int code = dbHandler.loginUser(username.getText().toString(), password.getText().toString());

                switch (code) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "Fields Cannot be Empty!", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "Admin Logged IN!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AddMovieActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "User Logged IN!", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getApplicationContext(), MovieListActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Login Failed => Invalid Username or Password!"
                                , Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());
                String type;
                if (username.getText().toString().equals("admin")) {
                    type = "ADMIN";
                } else {
                    type = "USER";
                }
                long newID = dbHandler.registerUser(username.getText().toString(), type, password.getText().toString());
                Toast.makeText(getApplicationContext(), "User Registered Successfully : " + newID, Toast.LENGTH_SHORT).show();
            }
        });
    }
}