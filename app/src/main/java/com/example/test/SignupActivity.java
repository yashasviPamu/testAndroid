package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
EditText email,pass,cpass;
Button signUp;
TextView login;
FirebaseAuth f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email=findViewById(R.id.et2);
        pass=findViewById(R.id.et5);
        cpass=findViewById(R.id.et6);
        signUp=findViewById(R.id.bt1);
        login=findViewById(R.id.login);
        login.setOnClickListener(this);
        signUp.setOnClickListener(this);
        f=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt1:
                final String mail, password, confirmpassword;
                mail = email.getText().toString();
                password = pass.getText().toString();
                confirmpassword=cpass.getText().toString();
                if(password.equals(confirmpassword)) {
                    f.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            f.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(),"Verification Link sent successfully",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), task1.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please enter same password twice",Toast.LENGTH_SHORT);
                }
                break;

            case R.id.login:
                startActivity(new Intent(SignupActivity.this,SigninActivity.class));
        }
    }
}