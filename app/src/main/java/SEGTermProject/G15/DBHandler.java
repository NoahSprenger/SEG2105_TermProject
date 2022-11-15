package SEGTermProject.G15;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class DBHandler {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth Auth = FirebaseAuth.getInstance();
    private String userID;

    DBHandler(){}

    public void addUser(User user) throws Exception {
        Log.d("TEST", Auth.getCurrentUser().getUid());
        if(Auth.getCurrentUser() != null){
            userID = Auth.getCurrentUser().getUid();
            Auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        userID = Auth.getCurrentUser().getUid();
                        Map<String, Object> User = new HashMap<>();
                        User.put("Type", user.getType());
                        User.put("Username", user.getUsername());
                        User.put("Email", user.getEmail());
                        User.put("Password", user.getPassword());
                        firestore.collection("Users").document(userID).set(User);
                    }
                }
            });
        }
    }

    public void addCourse(String CourseID, String CourseName){
        Map<String, Object> Course = new HashMap<>();
        Course.put("CourseID", CourseID);
        Course.put("CourseName", CourseName);
        Course.put("Instructor", "");
        Course.put("Day1", "");
        Course.put("Day2", "");
        Course.put("Day1Hours", "");
        Course.put("Day2Hours", "");
        Course.put("StudentCapacity", "");
        Course.put("Description", "");
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

    public Stack<String> getUsers(){
        Stack<String> users = new Stack<String>();
        CollectionReference UsersRef = firestore.collection("Users");
        UsersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String user = document.getString("Email");
                        users.add(user);

                    }
                }
            }
        });
        return  users;
    }

    public Stack<String> getCourses(){
        Stack<String> Courses = new Stack<String>();
        CollectionReference CourseRef = firestore.collection("Courses");
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String course = document.getString("CourseID");
                        Courses.add(course);

                    }
                }
            }
        });
        return  Courses;
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

    public void editCourse(String CourseID, String NewCourseID, String NewCourseInfo) {
        CollectionReference UsersRef = firestore.collection("Courses");
//        All fields are filled
        if (!CourseID.isEmpty() && !NewCourseID.isEmpty() && !NewCourseInfo.isEmpty()) {
            Query query = UsersRef.whereEqualTo("CourseID", CourseID);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String docId = document.getId();
                            firestore.collection("Courses").document(docId).update("CourseID", NewCourseID);
                            firestore.collection("Courses").document(docId).update("CourseName", NewCourseInfo);
                        }
                    }
                }
            });
        } else if (!CourseID.isEmpty() && !NewCourseID.isEmpty()) { // only id is filled
            Query query = UsersRef.whereEqualTo("CourseID", CourseID);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String docId = document.getId();
                            firestore.collection("Courses").document(docId).update("CourseID", NewCourseID);
                        }
                    }
                }
            });
        } else { // only info is filled
            Query query = UsersRef.whereEqualTo("CourseID", CourseID);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String docId = document.getId();
                            firestore.collection("Courses").document(docId).update("CourseName", NewCourseInfo);
                        }
                    }
                }
            });
        }
    }

    

}
