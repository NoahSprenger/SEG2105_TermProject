package SEGTermProject.G15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Activity class for instructor assignment utilities. 
 * Extends AppCompactActivity.
 */
public class InstructorAssignmentActivity extends AppCompatActivity {

    ListView courseList;
    SimpleAdapter adapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String line, courseID, courseName, instructor;
    EditText CourseSelect;
    Button btnTeach, btnSearch, btnBack;

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_assignment);
        String username = getIntent().getStringExtra("username");
        courseList = (ListView) findViewById(R.id.CourseList);
        btnTeach = findViewById(R.id.btnTeach);
        btnSearch = findViewById(R.id.btnSearch);
        btnBack = findViewById(R.id.btnBack);
        CourseSelect = findViewById((R.id.CourseSelect));

        List<HashMap<String, String>> results = new ArrayList<>();
        adapter = new SimpleAdapter(InstructorAssignmentActivity.this, results, R.layout.custom_row_view_instructor,
                new String[]{"line1", "line2", "line3"},
                new int[]{R.id.courseID, R.id.courseName, R.id.instructName});


        CollectionReference CourseRef = firestore.collection("Courses");
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        courseID = document.getString("CourseID");
                        courseName = document.getString("CourseName");
                        instructor = document.getString("Instructor");
                        HashMap<String, String> courseValues = new HashMap<>();
                        courseValues.put("line1", courseID);
                        courseValues.put("line2", courseName);

                        if (instructor.equals("")) {
                            courseValues.put("line3", "No instructor set");
                        } else {
                            courseValues.put("line3", instructor);
                        }
                        if (line == null){
                            Log.e("Null","Error");
                        }
                        results.add(courseValues);
                    }
                }
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseList.setAdapter(adapter);
            }
        });

        btnTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Course = CourseSelect.getText().toString();
                CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                courseID = document.getString("CourseID");
                                courseName = document.getString("CourseName");
                                instructor = document.getString("Instructor");

                                if ((instructor.equals("") && courseID.equals(Course)) || (instructor.equals("") && courseName.equals(Course)) ) {
                                    firestore.collection("Courses").document(document.getId()).update("Instructor", username);
                                    Toast.makeText(InstructorAssignmentActivity.this, courseID +", "+ courseName+ "Assigned", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(InstructorAssignmentActivity.this, InstructorActivity.class);
                                    intent.putExtra("username",username);
                                    startActivity(intent);
                                    finish();
                                }else if (courseName.equals(Course) || courseID.equals(Course)){
                                    CourseSelect.setError("Select open course");
                                    Toast.makeText(InstructorAssignmentActivity.this, "Selected course is closed", Toast.LENGTH_SHORT).show();

                                }

                            }

                        }

                    }
                });
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String course = CourseSelect.getText().toString();
                List<HashMap<String, String>> searchRslt = new ArrayList<>();
                adapter = new SimpleAdapter(InstructorAssignmentActivity.this, searchRslt, R.layout.custom_row_view_instructor,
                        new String[]{"line1", "line2", "line3"},
                        new int[]{R.id.courseID, R.id.courseName, R.id.instructName});

                CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                courseID = document.getString("CourseID");
                                courseName = document.getString("CourseName");
                                instructor = document.getString("Instructor");
                                HashMap<String, String> courseValues = new HashMap<>();

                                courseValues.put("line1", courseID);
                                courseValues.put("line2", courseName);

                                if (instructor.equals("")) {
                                    courseValues.put("line3", "No instructor set");
                                } else {
                                    courseValues.put("line3", instructor);
                                }

                                if(courseID.equals(course) || courseName.equals(course)){
                                    searchRslt.add(courseValues);
                                }
                                else{
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
                Intent intent = new Intent(InstructorAssignmentActivity.this, InstructorActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}