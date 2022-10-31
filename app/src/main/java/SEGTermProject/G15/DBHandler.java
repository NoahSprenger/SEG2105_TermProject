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

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth Auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(); ;

    public Boolean hasDup, wait;
    public DataSnapshot DS;
    private String userID;

    DBHandler(){

    }



    DBHandler(DataSnapshot ds){
        DS = ds;
    }

    public DataSnapshot getDS(){
    return DS;
    }





    public void addStudent(User user) throws Exception {
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

                    }else{
                        try {
                            throw new Exception("Has dup");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }











//        Log.d("AT ADD STUDENT", hasDup.toString());
//        if (hasDup){
//            throw new Exception("Has Dup");
//        }
//        Map<String, Object> User = new HashMap<>();
//
//        User.put("Type", "Student");
//        User.put("Username", user.getUserName());
//        User.put("Email", user.getUserID());
//        User.put("Password", user.getPassword());
//
//
//        firestore.collection("Users").document(user.getUserName()).set(User);
//        return true;

    }

    public void addInstroctor(User user){
        Map<String, Object> User = new HashMap<>();
        //User.put("Key", "2");
        User.put("Type", "Instroctor");
        User.put("Username", user.getUsername());
        User.put("Email", user.getEmail());
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
    private Boolean validUser;
    private String Type;
    public Object[] Info = new Object[]{validUser,Type};

    public void validUser(String Username, String Password){

        validUser = false;
//        firestore.collection("Users").whereEqualTo("Username", Username).whereEqualTo("Password", Password).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (!value.isEmpty()){
//                    validUser = true;
//                    Log.e("VALIDUSER",validUser.toString());
//
//                }
//            }
//        });
        CollectionReference UsersRef = firestore.collection("Users");
        Query query = UsersRef.whereEqualTo("Username", Username).whereEqualTo("Password", Password);
        DateGetter DG = new DateGetter(query.get());
        QuerySnapshot qs = DG.QS;
        for (QueryDocumentSnapshot document : qs) {
            validUser = true;
            Type = document.get("Type").toString();
            Log.e("VALIDUSER", validUser.toString());
        }
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for (QueryDocumentSnapshot document : task.getResult()){
//                        validUser = true;
//                        Type = document.get("Type").toString();
//                        Log.e("VALIDUSER",validUser.toString());
//                    }
//                }
//            }
//        });




    }


}
