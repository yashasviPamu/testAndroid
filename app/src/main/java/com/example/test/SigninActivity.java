package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {
EditText phone,email,password;
Button verify, signin,signup;
String number;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        phone=findViewById(R.id.ed1);
        email=findViewById(R.id.ed2);
        mAuth = FirebaseAuth.getInstance();
        password=findViewById(R.id.ed3);
        verify=findViewById(R.id.b1);
        signin=findViewById(R.id.b2);
        signup=findViewById(R.id.b3);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = phone.getText().toString().trim();
                validNo(number);
                Intent intent = new Intent(SigninActivity.this,verifyActivity.class);
                intent.putExtra("mobile",number);
                startActivity(intent);
                Toast.makeText(SigninActivity.this,number, Toast.LENGTH_LONG).show();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail,pass;
                mail=email.getText().toString();
                pass=password.getText().toString();
                // .signInWithEmailAndPassword is used to check if the user has already created a
                // account with the application and if the account exists and verified by clicking
                // the email sent to his mail id then the user is redirected to profile page
                mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                startActivity(new Intent(SigninActivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Please Verify",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this,SignupActivity.class));
            }
        });
    }
    private void validNo(String no){
        if(no.isEmpty() || no.length() < 10){
            phone.setError("Enter a valid mobile");
            phone.requestFocus();
            return;
        }
    }
}