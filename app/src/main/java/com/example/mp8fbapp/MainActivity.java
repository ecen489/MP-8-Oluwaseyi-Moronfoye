package com.example.mp8fbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {



    Button createButton;
    Button loginButton;
    EditText emailText;
    EditText passwordText;

    FirebaseDatabase fbdb;
    DatabaseReference dbrf;

    FirebaseAuth mAuth;
    FirebaseUser user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();

        createButton=findViewById(R.id.create);
        loginButton=findViewById(R.id.login);
        emailText=findViewById(R.id.eID);
        passwordText=findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        final Intent pullAct = new Intent(this, PullActivity.class);


        Button createAcc = (Button) findViewById(R.id.create);
        createAcc.setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        String email = emailText.getText().toString();
                        String password = passwordText.getText().toString();

                        mAuth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){

                                            Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_SHORT).show();
                                            user = mAuth.getCurrentUser(); //The newly created user is already signed in
                                            pullAct.putExtra("email", emailText.getText().toString());
                                            pullAct.putExtra("password", passwordText.getText().toString());
                                            startActivity(pullAct);

                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });



        Button log = (Button) findViewById(R.id.login);
        log.setOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        String email = emailText.getText().toString();
                        String password = passwordText.getText().toString();

                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
                                            user = mAuth.getCurrentUser(); //The user is signed in
                                            pullAct.putExtra("email", emailText.getText().toString());
                                            pullAct.putExtra("password", passwordText.getText().toString());
                                            startActivity(pullAct);
                                        } else {
                                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });

    }

//    }

    public void createAcc(View view) {




    }

    public void login(View view) {



    }
}
