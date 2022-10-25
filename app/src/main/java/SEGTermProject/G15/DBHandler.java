package SEGTermProject.G15;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.*;

public class DBHandler {

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();;
    public DBHandler() {}



    public void addUser(String Type, String Username, String Password){

        Map<String, Object> User = new HashMap<>();
        //User.put("Key", "2");
        User.put("Type", Type);
        User.put("Username", Username);
        User.put("Password", Password);

        firestore.collection("Users").add(User);
    }

    public void addCourse(String CourseID, String CourseName){

        Map<String, Object> Course = new HashMap<>();
        //Course.put("Key", "1");
        Course.put("CourseID", CourseID);
        Course.put("CourseName", CourseName);

        firestore.collection("Courses").add(Course);
    }

    public void deleteUser(String Username){

        CollectionReference UsersRef = firestore.collection("Users");
        Query query = UsersRef.whereEqualTo("Username", Username);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String docId = document.getId();
                        firestore.collection("Users").document(docId).delete();
                    }
                }
            }
        });
    }

    public void deleteCourse(String CourseID){

        CollectionReference UsersRef = firestore.collection("Courses");
        Query query = UsersRef.whereEqualTo("CourseID", CourseID);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String docId = document.getId();
                        firestore.collection("Courses").document(docId).delete();
                    }
                }
            }
        });
    }
}
