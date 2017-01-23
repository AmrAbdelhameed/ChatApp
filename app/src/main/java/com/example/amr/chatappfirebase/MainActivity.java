package com.example.amr.chatappfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText txtEmailLogin;
    private EditText txtPwd;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, Room.class));
            finish();
        }

        setContentView(R.layout.activity_main);

        txtEmailLogin = (EditText) findViewById(R.id.txtEmailLogin);
        txtPwd = (EditText) findViewById(R.id.txtPasswordLogin);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void btnUserLogin_Click(View v) {

        final String email = txtEmailLogin.getText().toString();

        if (txtEmailLogin.getText().toString().isEmpty() && txtPwd.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Forget Enter Your Email and Password", Toast.LENGTH_SHORT).show();
        } else if (txtEmailLogin.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Forget Enter Your Email", Toast.LENGTH_SHORT).show();
        } else if (txtPwd.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Forget Enter Your Password", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Please wait...", "Proccessing...", true);

            (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(), txtPwd.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(MainActivity.this, Room.class);
                                i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                                startActivity(i);
                                finish();
                            } else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void btnRegister(View v) {
        Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(i);
    }

    public void btnForget(View v) {
        Intent i = new Intent(MainActivity.this, ForgetPassword.class);
        startActivity(i);
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }

}
