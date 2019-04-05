package com.example.mp8fbapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PushActivity extends AppCompatActivity {

    FirebaseDatabase fbdb;
    DatabaseReference dbrf;
    int radioID = R.id.bart;
    int dbID = 123;
    FirebaseAuth mAuth;
    FirebaseUser user = null;

    EditText courseID;
    String cID;
    EditText courseName;
    String cName;
    EditText grade;
    String g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        courseID = findViewById(R.id.courseID);
        courseName = findViewById(R.id.courseName);
        grade = findViewById(R.id.grade);


        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        mAuth.signInWithEmailAndPassword(email, password);
        user = mAuth.getCurrentUser();
    }

    public void PushnReturn(View view) {
        if(user!=null) {

            cID = courseID.getText().toString();
            cName = courseName.getText().toString();
            g = grade.getText().toString();

            DatabaseReference gradeObj = dbrf.child("simpsons/grades/");
            DatabaseReference newGrade = gradeObj.push();
            newGrade.child("student_id").setValue(dbID);

            newGrade.child("course_id").setValue(Integer.valueOf(cID));

            newGrade.child("grade").setValue(g);

            newGrade.child("course_name").setValue(cName);

            Toast.makeText(getApplicationContext(),"Sucess!",Toast.LENGTH_SHORT).show();
        }

        else{

            Toast.makeText(getApplicationContext(),"Please login",Toast.LENGTH_SHORT).show();
        }

        finish();

    }

    public void radioClick(View view) {
        radioID = view.getId();
        if(radioID==R.id.bart){dbID = 123;}
        if(radioID==R.id.lisa){dbID = 888;}
        if(radioID==R.id.milhouse){dbID = 456;}
        if(radioID==R.id.ralph){dbID = 404;}


    }
}
