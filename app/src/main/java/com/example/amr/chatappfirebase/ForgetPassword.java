package com.example.amr.chatappfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    EditText forgot_emailEditText;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        forgot_emailEditText = (EditText) findViewById(R.id.Email_Reset_EditText);
        resetPassword = (Button) findViewById(R.id.reset_btn);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = forgot_emailEditText.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(ForgetPassword.this, "Please, Enter your registered Email", Toast.LENGTH_LONG).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(ForgetPassword.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(ForgetPassword.this, "Failed to send reset Email!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(ForgetPassword.this, "We have sent you instructions to reset your password on mail!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(ForgetPassword.this, MainActivity.class));
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
