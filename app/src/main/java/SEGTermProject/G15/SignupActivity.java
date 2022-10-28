package SEGTermProject.G15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.*;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    EditText Username, Password;
    Button signupBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);

        signupBtn = findViewById(R.id.signupBtn);

        DBHandler db = new DBHandler();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = Username.getText().toString();
                String password = Password.getText().toString();

                db.addUser("Student", userName, password);
            }
        });


    }
}