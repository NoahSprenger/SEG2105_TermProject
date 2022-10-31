package SEGTermProject.G15;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
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

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    public DBHandler() {
        result = false;
    }
    private Boolean result;

    private void isDuplicate(User user){
        result = false;
        CollectionReference UsersRef = firestore.collection("Users");
        Query query = UsersRef.whereEqualTo("Username", user.getUserName());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.e("T1",result.toString());
                result = true;
                if(task.isSuccessful()){
                    Log.e("T2",result.toString());
                    result = true;
                    for (QueryDocumentSnapshot document : task.getResult()){
                        result = true;
                        Log.e("T3",result.toString());

                    }
                }
            }
        });
        Query query2 = UsersRef.whereEqualTo("Email", user.getUserID());
        query2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        result = true;
                        Log.e("Email",result.toString());
                    }
                }
            }
        });
        Log.e("FINAL",result.toString());
    }

    public boolean hasDuplicate(User user){
        isDuplicate(user);
        Log.e("FFFFFINAL",result.toString());
        return result;
    }

    public void addStudent(User user){
        Map<String, Object> User = new HashMap<>();

        User.put("Type", "Student");
        User.put("Username", user.getUserName());
        User.put("Email", user.getUserID());
        User.put("Password", user.getPassword());

        firestore.collection("Users").add(User);

    }

    public void addInstroctor(User user){
        Map<String, Object> User = new HashMap<>();
        //User.put("Key", "2");
        User.put("Type", "Instroctor");
        User.put("Username", user.getUserName());
        User.put("Email", user.getUserID());
        User.put("Password", user.getPassword());

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
                            firestore.collection("Courses").document(docId).update("CourseInfo", NewCourseInfo);
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
                            firestore.collection("Courses").document(docId).update("CourseInfo", NewCourseInfo);
                        }
                    }
                }
            });
        }
    }
}
