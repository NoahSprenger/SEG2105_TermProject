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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPassword;
    ProgressBar progressBar;
    Button btnSignUp, btnSignIn;
    String usernameInput, emailInput, passwordInput;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.SignInEmail);
        edtPassword = findViewById(R.id.SignInPassword);
        progressBar = findViewById(R.id.signInProgressBar);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        mAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailInput = edtEmail.getText().toString().trim();
                passwordInput = edtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(emailInput)){
                    edtEmail.setError("Please enter your email address");
                }
                else if(!emailInput.matches(emailPattern)){
                    edtEmail.setError("Please enter a valid email address");
                }
                else if(TextUtils.isEmpty(passwordInput)){
                    edtPassword.setError("Please enter your password");
                }
                else{
                    loginUser();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void loginUser(){
        progressBar.setVisibility(View.VISIBLE);
        btnSignIn.setVisibility(View.INVISIBLE);

        mAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                btnSignIn.setVisibility(View.VISIBLE);
            }
        });
    }
}