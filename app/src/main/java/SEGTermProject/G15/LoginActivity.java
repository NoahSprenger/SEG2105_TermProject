package SEGTermProject.G15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth Auth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private EditText edtUsername, edtPassword;
    private ProgressBar progressBar;
    private Button btnSignUp, btnSignIn;
    private String usernameInput,  passwordInput;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
    private DBHandler db = new DBHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.SignInUsername);
        edtPassword = findViewById(R.id.SignInPassword);
        progressBar = findViewById(R.id.signInProgressBar);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        mAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usernameInput = edtUsername.getText().toString();
                passwordInput = edtPassword.getText().toString();

                Log.e("USERNAME", usernameInput);
                Log.e("PASSWORD", passwordInput);

                if (usernameInput.isEmpty()) {
                    edtUsername.setError("Please enter your username");
                    Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (passwordInput.isEmpty()) {
                    edtPassword.setError("Please enter your password");
                }else if(!usernameInput.matches(emailPattern) && ! usernameInput.equals("admin")){
                        edtUsername.setError("Please enter a valid email address");
                        Toast.makeText(LoginActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    if(usernameInput.equals("admin")){
                        usernameInput = "admin@gmail.com";
                    }
                    Auth.signInWithEmailAndPassword(usernameInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "LOGIN FAIL", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
                                String UserID = Auth.getCurrentUser().getUid();
                                firestore.collection("Users").document(UserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        Log.e("IM HERE", "HI");
                                        String Type = task.getResult().getString("Type");
                                        String Username = task.getResult().getString("Username");
                                        Toast.makeText(LoginActivity.this, Type, Toast.LENGTH_SHORT).show();
                                        if (Type.equals("Admin")){
                                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else if (Type.equals("Student")) {
                                            Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else if (Type.equals("Instructor")) {
                                            Intent intent = new Intent(LoginActivity.this, InstructorActivity.class);
                                            intent.putExtra("username", Username);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });


                            }
                        }
                    });
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
}




