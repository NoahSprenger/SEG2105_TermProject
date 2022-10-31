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
    FirebaseAuth Auth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener AuthLister  ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(); ;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    EditText edtUsername, edtEmail, edtPassword;
    ProgressBar progressBar;
    Button btnSignUp, btnSignIn;
    String usernameInput, emailInput, passwordInput;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
    DBHandler db = new DBHandler();

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

                if(usernameInput.isEmpty()){
                    edtUsername.setError("Please enter your username");
                    Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                }
                else if(passwordInput.isEmpty()){
                    edtPassword.setError("Please enter your password");
                }
                else {
                    //loginUser();
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
                                        Log.e("IM HERE","HI");
                                        String Type = task.getResult().getString("Type");
                                        Toast.makeText(LoginActivity.this, Type, Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        }
                    });
                }
            }
        });


//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });



//    private void loginUser(){
//
//        db.validUser(usernameInput, passwordInput);
////       finish();
////        overridePendingTransition(0,0);
////        startActivity(getIntent());
////        overridePendingTransition(0,0);
//
//        Log.e("DO I GET HERE","YES");
//        if (db.Info[0].equals(true)){
////            if(Info[1].equals("Student")){
////                Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
////                startActivity(intent);
////                finish();
////            }else if (Info[1].equals("Instructor")){
////                Intent intent = new Intent(LoginActivity.this, InstructorActivity.class);
////                startActivity(intent);
////                finish();
////            }else{
////
////                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
////                startActivity(intent);
////                finish();
////            }
//            Toast.makeText(LoginActivity.this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
//            progressBar.setVisibility(View.VISIBLE);
//            btnSignIn.setVisibility(View.INVISIBLE);
//        }else {
//            edtUsername.setError("Wrong Username or Password");
//            edtEmail.setError("Wrong Username or Password");
//            Toast.makeText(LoginActivity.this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
//        }
//
//
//
////        mAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
////            @Override
////            public void onSuccess(AuthResult authResult) {
////                Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                startActivity(intent);
////                finish();
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
////                progressBar.setVisibility(View.INVISIBLE);
////                btnSignIn.setVisibility(View.VISIBLE);
////            }
////        });
    }}
