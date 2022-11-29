package SEGTermProject.G15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.*;

/**
 * Activity class for application entry.
 * Extends AppCompactActivity.
 */
public class MainActivity extends AppCompatActivity {

    private Button btnSignUp, btnSignIn;
    private FirebaseAuth Auth;
    private FirebaseAuth.AuthStateListener AuthLister;

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireAuthSetup();
        setContentView(R.layout.activity_main);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * Firebase setup.
     */
    private void FireAuthSetup(){
        Auth = FirebaseAuth.getInstance();
        AuthLister = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged (@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user =  firebaseAuth.getCurrentUser();
                if(user != null){

                }else{

                }
            }
        };
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart(){
        super.onStart();
        Auth.addAuthStateListener(AuthLister);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void onStop(){
        super.onStop();
        if (AuthLister != null) {
            Auth.removeAuthStateListener(AuthLister);
        }
    }



}
