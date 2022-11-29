package SEGTermProject.G15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentCoursesActivity extends AppCompatActivity {

    ListView courseList;
    SimpleAdapter adapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String courseID, courseName, instructor, courseDescription, courseCapacity, courseDate1, courseDate2;
    EditText CourseSelect, CourseDay;
    Button btnSearch, btnUnEnrol, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_courses);
        String username = getIntent().getStringExtra("username");

        courseList = (ListView) findViewById(R.id.CourseList);
        btnUnEnrol = findViewById(R.id.btnUnEnrol);
        btnSearch = findViewById(R.id.btnSearch);
        btnBack = findViewById(R.id.btnBack);
        CourseSelect = findViewById(R.id.CourseSelect);

        List<HashMap<String, String>> results = new ArrayList<>();
        adapter = new SimpleAdapter(StudentCoursesActivity.this, results, R.layout.custom_row_view_student,
                new String[]{"line1", "line2", "line3", "line4", "line5", "line6", "line7"},
                new int[]{R.id.courseID, R.id.courseName, R.id.instructName,
                        R.id.courseDescription, R.id.courseDate1, R.id.courseDate2, R.id.courseCapacity});

        CollectionReference CourseRef = firestore.collection("Courses");
        Query enrolledCourseQuery = CourseRef.whereIn("Students", Arrays.asList(username));
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){

                        HashMap<String, String> courseValues = new HashMap<>();


                        DocumentReference documentReference = firestore.collection("Courses").document(document.getId());
                        Log.e("errCHECK", documentReference.getId());
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Log.e("errCHECK", documentSnapshot.getId());
                                if (documentSnapshot.exists()){
                                    Log.e("errCHECK", "document EXISTS passed if");
                                    ArrayList<String> studentList = (ArrayList) documentSnapshot.get("Students");
                                    if (studentList != null) {
                                        Log.e("errCHECK", String.valueOf(studentList.size()));
                                    }

                                    if(studentList != null) {
                                        if (!studentList.isEmpty() && studentList.contains(username)) {
                                            courseID = document.getString("CourseID");
                                            courseName = document.getString("CourseName");
                                            instructor = document.getString("Instructor");
                                            courseDescription = document.getString("Description");
                                            courseCapacity = document.getString("StudentCapacity");
                                            courseDate1 = document.getString("Day1") + ", " + document.getString("Day1Hours");
                                            courseDate2 = document.getString("Day2") + ", " + document.getString("Day2Hours");

                                            courseValues.put("line1", courseID);
                                            courseValues.put("line2", courseName);
                                            courseValues.put("line3", instructor);
                                            courseValues.put("line4", courseDescription);
                                            courseValues.put("line5", courseCapacity);
                                            courseValues.put("line6", courseDate1);
                                            courseValues.put("line7", courseDate2);
                                        }
                                    }
                                }
                            }
                        });

                        results.add(courseValues);
                    }

                }
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseList.setAdapter(adapter);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Course = CourseSelect.getText().toString();
                String day = CourseDay.getText().toString();
                List<HashMap<String, String>> searchRslt = new ArrayList<>();
                adapter = new SimpleAdapter(StudentCoursesActivity.this, searchRslt, R.layout.custom_row_view_student,
                        new String[]{"line1", "line2", "line3", "line4", "line5", "line6", "line7"},
                        new int[]{R.id.courseID, R.id.courseName, R.id.instructName,
                                R.id.courseDescription, R.id.courseDate1, R.id.courseDate2, R.id.courseCapacity});
                CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                courseID = document.getString("CourseID");
                                courseName = document.getString("CourseName");
                                instructor = document.getString("Instructor");
                                courseDate1 = document.getString("Day1") + ", " + document.getString("Day1Hours");
                                courseDate2 = document.getString("Day2") + ", " + document.getString("Day2Hours");
                                HashMap<String, String> courseValues = new HashMap<>();

                                courseValues.put("line1", courseID);
                                courseValues.put("line2", courseName);
                                courseValues.put("line3", instructor);
                                courseValues.put("line4", courseDescription);
                                courseValues.put("line5", courseCapacity);
                                courseValues.put("line6", courseDate1);
                                courseValues.put("line7", courseDate2);

                                if(!Course.isEmpty()){
                                    if(Course.equals(courseName) || Course.equals(courseID)){
                                        searchRslt.add(courseValues);
                                    }
                                } else{
                                    results.remove(courseValues);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        courseList.setAdapter(adapter);
                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentCoursesActivity.this, StudentActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });

    }
}