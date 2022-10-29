package SEGTermProject.G15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPassword;
    ProgressBar progressBar;
    Button btnSignUp, btnSignIn;
    RadioGroup radioGroup;
    RadioButton radioInstructor, radioStudent;
    String usernameInput, emailInput, passwordInput;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtUsername = findViewById(R.id.edtSignUpUsername);
        edtEmail = findViewById(R.id.edtSignUpEmail);
        edtPassword = findViewById(R.id.edtSignUpPassword);
        progressBar = findViewById(R.id.signUpProgressBar);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
        radioInstructor = findViewById(R.id.radioInstructor);
        radioStudent = findViewById(R.id.radioStudent);

        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameInput = edtUsername.getText().toString();
                emailInput = edtEmail.getText().toString();
                passwordInput = edtPassword.getText().toString();

                if(TextUtils.isEmpty(usernameInput)){
                   edtUsername.setError("Please enter a username");
                }
                else if(TextUtils.isEmpty(emailInput)){
                    edtEmail.setError("Please enter an email");
                }
                else if(!emailInput.matches(emailPattern)){
                    edtEmail.setError("Please enter a valid email address");
                }
                else if(TextUtils.isEmpty(passwordInput)){
                    edtPassword.setError("Please enter a password");
                }
                else{
                    createUser();
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void createUser(){
        progressBar.setVisibility(View.VISIBLE);
        btnSignUp.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(SignUpActivity.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                btnSignUp.setVisibility(View.VISIBLE);
            }
        });
    }
}