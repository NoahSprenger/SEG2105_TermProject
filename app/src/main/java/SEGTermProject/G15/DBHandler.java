package SEGTermProject.G15;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.firestore.FieldValue;
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
                        if (user.getType().equals("Student")){
                            Map<String, String[]> Courses = new HashMap<>();
                            User.put("Courses", Courses);
                        }
                        firestore.collection("Users").document(userID).set(User);
                    }
                }
            });
        }
    }

    public void UnenrollStudent (String username, String Item){
        CollectionReference UserRef = firestore.collection("Users");
        UserRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        if (document.getString("Username").equals(username)) {
                            Map<String, ArrayList> Courses = (Map<String, ArrayList>) document.get("Courses");
                            Courses.remove(Item);
                            firestore.collection("Users").document(document.getId()).update("Courses", Courses);
//                            Toast.makeText(StudentCoursesActivity.this, Item+ "Unenrolled", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(StudentCoursesActivity.this, StudentActivity.class);
//                            intent.putExtra("username", username);
//                            startActivity(intent);
//                            finish();
                        }

                    }
                }
            }
        });
        CollectionReference CourseRef = firestore.collection("Courses");
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        if (document.getString("CourseID").equals(Item)){
                            ArrayList Students = (ArrayList) document.get("Students");
                            Students.remove(username);
                            firestore.collection("Courses").document(document.getId()).update("Students", Students);

                        }
                    }
                }
            }
        });
    }

    public void EnrollStudent ( String username, String Item, ArrayList<String[]> ScheduleList){

        String Course = Item;
        CollectionReference CourseRef = firestore.collection("Courses");
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String courseID, courseName, instructor, courseDate1, courseDate2;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("CourseID").equals(Item)) {
                            courseID = document.getString("CourseID");
                            courseName = document.getString("CourseName");
                            instructor = document.getString("Instructor");
                            courseDate1 = document.getString("Day1") + ", " + document.getString("Day1Hours");
                            courseDate2 = document.getString("Day2") + ", " + document.getString("Day2Hours");

                            Boolean FitsSchedule = true;
                            for (int i = 0; i < ScheduleList.size(); i++) {
                                String[] temp = ScheduleList.get(i);
                                if (temp[0].equals(document.getString("Day1")) && temp[1].equals(document.getString("Day1Hours")) ||
                                        temp[2].equals(document.getString("Day2")) && temp[3].equals(document.getString("Day2Hours"))) {
                                    FitsSchedule = false;
//                                    CourseSelect.setError("Select a course with an unoccupied time slot");
//                                    Toast.makeText(StudentEnrolActivity.this, "Selected course is time slot in occupied", Toast.LENGTH_SHORT).show();
                                }
                            }

                            if (String.valueOf(((ArrayList)document.get("Students")).size()).equals(document.getString("StudentCapacity"))){
//                                CourseSelect.setError("This course is full");
                            }

                            else if (FitsSchedule && (!instructor.equals("") && courseID.equals(Item)) || (!instructor.equals("") && courseName.equals(Item))) {
                                firestore.collection("Courses").document(
                                        document.getId()).update("Students", FieldValue.arrayUnion(username));
                                CollectionReference UserRef = firestore.collection("Users");
                                UserRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot Userdocument : task.getResult()) {
                                                if (Userdocument.getString("Username").equals(username)) {
                                                    Log.e("USER", Userdocument.getString("Username"));
                                                    Map Schedule = (Map) Userdocument.get("Courses");
                                                    String CourseID = document.getString("CourseID");
                                                    ArrayList Days = new ArrayList<>();
                                                    Days.add(document.getString("Day1"));
                                                    Days.add(document.getString("Day1Hours"));
                                                    Days.add(document.getString("Day2"));
                                                    Days.add(document.getString("Day2Hours"));
                                                    Schedule.put(CourseID, Days);
                                                    firestore.collection("Users").document(Userdocument.getId()).update("Courses", Schedule);}
                                            }
                                        }
                                    }
                                });
//                                Toast.makeText(StudentEnrolActivity.this, courseID + ", " + courseName + "Enrolled", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(StudentEnrolActivity.this, StudentActivity.class);
//                                intent.putExtra("username", username);
//                                startActivity(intent);
//                                finish();
                            } else if ((instructor.equals("") && courseID.equals(Course)) || (instructor.equals("") && courseName.equals(Course))) {
//                                CourseSelect.setError("Select a course with an instructor");
//                                Toast.makeText(StudentEnrolActivity.this, "Selected course is closed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
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
