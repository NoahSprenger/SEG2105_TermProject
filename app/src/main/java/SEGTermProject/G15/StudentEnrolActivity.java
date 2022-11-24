package SEGTermProject.G15;

import static com.google.firebase.firestore.FieldValue.arrayUnion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentEnrolActivity extends AppCompatActivity {

    ListView courseList;
    SimpleAdapter adapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String courseID, courseName, instructor, courseDescription, courseCapacity, courseDate1, courseDate2;
    EditText CourseSelect, CourseDay;
    Button btnSearch, btnEnrol, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enrol);
        String username = getIntent().getStringExtra("username");
        courseList = (ListView) findViewById(R.id.CourseList);
        btnEnrol = findViewById(R.id.btnEnrol);
        btnSearch = findViewById(R.id.btnSearch);
        btnBack = findViewById(R.id.btnBack);
        CourseSelect = findViewById(R.id.CourseSelect);
        CourseDay = findViewById(R.id.CourseDay);

        List<HashMap<String, String>> results = new ArrayList<>();
        adapter = new SimpleAdapter(StudentEnrolActivity.this, results, R.layout.custom_row_view_student,
                new String[]{"line1", "line2", "line3", "line4", "line5", "line6", "line7"},
                new int[]{R.id.courseID, R.id.courseName, R.id.instructName,
                        R.id.courseDescription, R.id.courseDate1, R.id.courseDate2, R.id.courseCapacity});

        CollectionReference CourseRef = firestore.collection("Courses");
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                   for (QueryDocumentSnapshot document : task.getResult()){
                       courseID = document.getString("CourseID");
                       courseName = document.getString("CourseName");
                       instructor = document.getString("Instructor");
                       courseDescription = document.getString("Description");
                       courseCapacity = document.getString("StudentCapacity");
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
                       results.add(courseValues);
                   }
                }
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseList.setAdapter(adapter);
            }
        });

        btnEnrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Course = CourseSelect.getText().toString();
                CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                courseID = document.getString("CourseID");
                                courseName = document.getString("CourseName");
                                instructor = document.getString("Instructor");
                                courseDate1 = document.getString("Day1") + ", " + document.getString("Day1Hours");
                                courseDate2 = document.getString("Day2") + ", " + document.getString("Day2Hours");
                                if (!Course.isEmpty()) {
                                    if ((!instructor.equals("") && courseID.equals(Course)) || (!instructor.equals("") && courseName.equals(Course))) {
                                        firestore.collection("Courses").document(
                                                document.getId()).update("Students", FieldValue.arrayUnion(username));
                                        Toast.makeText(StudentEnrolActivity.this, courseID + ", " + courseName + "Enrolled", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(StudentEnrolActivity.this, StudentActivity.class);
                                        intent.putExtra("username", username);
                                        startActivity(intent);
                                        finish();
                                    } else if ((instructor.equals("") && courseID.equals(Course)) || (instructor.equals("") && courseName.equals(Course))) {
                                        CourseSelect.setError("Select a course with an instructor");
                                        Toast.makeText(StudentEnrolActivity.this, "Selected course is closed", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(StudentEnrolActivity.this, "Please enter a course to enrol in", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });
    }
}