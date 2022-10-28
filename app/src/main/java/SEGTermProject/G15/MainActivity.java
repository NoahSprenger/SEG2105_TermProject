package SEGTermProject.G15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.*;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        opensignup();


//        DBHandler db = new DBHandler();
//        db.deleteUser("Sam");
//        db.deleteCourse("ITI1100");
//        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

    }
    public void opensignup(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
