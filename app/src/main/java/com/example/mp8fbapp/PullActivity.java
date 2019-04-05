package com.example.mp8fbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PullActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<String> myDataset = new ArrayList<>() ;

    String studName;
    String courseName;
    String courseGrade;
    Grade grade;
    Student stud;
    String email;
    String password;


    FirebaseDatabase fbdb;
    DatabaseReference dbrf;

    FirebaseAuth mAuth;
    FirebaseUser user = null;
    EditText IDText;

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);

        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        mAuth.signInWithEmailAndPassword(email, password);
        user = mAuth.getCurrentUser();

        IDText=findViewById(R.id.studentID);






        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView


        // use a linear layout manager

//        final String[] items = new String[numItems];






         // set up the RecyclerView
        recyclerView = findViewById(R.id.RView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int orientation = new LinearLayoutManager(this).getOrientation();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                orientation);
        recyclerView.addItemDecoration(dividerItemDecoration);



        final Intent pushAct = new Intent(this, PushActivity.class);
        Button push = (Button) findViewById(R.id.push);
        push.setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        pushAct.putExtra("email", email);
                        pushAct.putExtra("password", password);
                        startActivity(pushAct);
                        //start push activity


                    }
                });


    }

    public void query1(View view) {
        if(user!=null) {

            myDataset.clear();
            int studID = Integer.parseInt(IDText.getText().toString());

            DatabaseReference gradeKey = dbrf.child("simpsons/grades/");

            Query query = gradeKey.orderByChild("student_id").equalTo(studID);
            query.addListenerForSingleValueEvent(valueEventListener1);


        }

        else{

            Toast.makeText(getApplicationContext(),"Please login",Toast.LENGTH_SHORT).show();
        }
    }

    public void query2(View view) {
        if(user!=null) { //user!=null

            myDataset.clear();

            int studID = Integer.parseInt(IDText.getText().toString());

            DatabaseReference gradeKey = dbrf.child("simpsons/grades/");

            Query query = gradeKey.orderByChild("student_id").startAt(studID);
            query.addListenerForSingleValueEvent(valueEventListener2);

        }

        else{

            Toast.makeText(getApplicationContext(),"Please login",Toast.LENGTH_SHORT).show();
        }
    }

    public void push(View view) {

    }

    public void signout(View view) {
        mAuth.signOut();
        user = null;
        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
        finish();
        //return to previous activity
    }



    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                int count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);
                        count += 1;
                    String courseName = grade.getcourse_name();
                    String courseGrade = grade.getgrade();
                    myDataset.add(courseName+", "+courseGrade);

                }

                adapter = new MyRecyclerViewAdapter(PullActivity.this, myDataset);
                recyclerView.setAdapter(adapter);

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            //log error

        }
    };

    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(getApplicationContext(),"listening",Toast.LENGTH_SHORT).show();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);

                    courseName = grade.getcourse_name();
                    courseGrade = grade.getgrade();
                    int studID = grade.getstudent_id();
                    String studIDS = Integer.toString(studID);


                    if(studID == 123)
                        studName = "Bart";
                    else if(studID == 404)
                        studName = "Ralph";
                    else if(studID == 456)
                        studName = "Milhouse";
                    else if(studID == 888)
                        studName = "Lisa";


///"+studIDS+"/name"

//                    DatabaseReference student = dbrf.child("simpsons/students/");
//                    Query query = student.orderByChild("id").equalTo(studID);
//
//                    query.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                Student stud = dataSnapshot.getValue(Student.class);
//                                studName = stud.getName();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });

//
//

//                    query.addListenerForSingleValueEvent(valueEventListener3);

                    myDataset.add(studName+", "+courseName+", "+ courseGrade);
                }
                adapter = new MyRecyclerViewAdapter(PullActivity.this, myDataset);
                recyclerView.setAdapter(adapter);

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            //log error

        }
    };

//    ValueEventListener valueEventListener3 = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            if (dataSnapshot.exists()) {
//                Student stud = dataSnapshot.getValue(Student.class);
//                studName = stud.getName();
//
//
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            //log error
//
//        }
//    };


}
