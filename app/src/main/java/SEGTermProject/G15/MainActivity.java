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

public class MainActivity extends AppCompatActivity {

    Button btnSignUp, btnSignIn;
    DBHandler db = new DBHandler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireAuthSetup();

//        Toast.makeText(MainActivity.this, "OPENED", Toast.LENGTH_SHORT).show();
//        DBHandler db = new DBHandler();
//        User Alec = new Student();
//        Alec.setUserName("Alec");
//        Boolean r =db.hasDuplicate(Alec);
//        Toast.makeText(MainActivity.this, r.toString(), Toast.LENGTH_SHORT).show();
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
    FirebaseAuth Auth;
    FirebaseAuth.AuthStateListener AuthLister;
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
    @Override
    public void onStart(){
        super.onStart();
        Auth.addAuthStateListener(AuthLister);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (AuthLister != null) {
            Auth.removeAuthStateListener(AuthLister);
        }
    }



}
