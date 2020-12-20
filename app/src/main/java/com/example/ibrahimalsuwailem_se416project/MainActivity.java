package com.example.ibrahimalsuwailem_se416project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText fName;
    EditText lName;
    EditText email;
    EditText phone;
    EditText userId;
    EditText userNo;
    Button add;
    Button delete;
    Button update;
    Button weather;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = myRef.child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fName = (EditText)findViewById(R.id.first);
        email = (EditText)findViewById(R.id.email);
        lName = (EditText)findViewById(R.id.last);
        phone = (EditText)findViewById(R.id.phone);
        userId = (EditText)findViewById(R.id.id);
        userNo = (EditText)findViewById(R.id.userNo);
        add = (Button)findViewById(R.id.button);
        delete = (Button)findViewById(R.id.button2);
        update = (Button)findViewById(R.id.button3);
        weather = (Button)findViewById(R.id.button4);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> userAdd = new HashMap<>();
                userAdd.put("emailAddress", email.getText().toString());
                userAdd.put("firstName", fName.getText().toString());
                userAdd.put("lastName", lName.getText().toString());
                userAdd.put("phoneNumber", phone.getText().toString());
                userAdd.put("userId", userId.getText().toString());
                usersRef.push().setValue(userAdd);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersRef.child(userNo.getText().toString()).removeValue();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> userUpdates = new HashMap<>();
                userUpdates.put(userNo+"/emailAddress", email.getText().toString());
                userUpdates.put(userNo+"/firstName", fName.getText().toString());
                userUpdates.put(userNo+"/lastName", lName.getText().toString());
                userUpdates.put(userNo+"/phoneNumber", phone.getText().toString());
                userUpdates.put(userNo+"/userId", userId.getText().toString());
                usersRef.updateChildren(userUpdates);
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });
    }
}