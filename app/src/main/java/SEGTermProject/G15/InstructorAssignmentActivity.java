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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Stack;

public class InstructorAssignmentActivity extends AppCompatActivity {

    ListView courseList;
    Stack Courses = new Stack();
    ArrayAdapter adapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String line, courseID, courseName, instructor;
    EditText CourseSelect;
    Button btnTeach;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_assignment);
        String username = getIntent().getStringExtra("username");
        courseList = findViewById(R.id.CourseList);
        btnTeach = findViewById(R.id.btnTeach);
        CourseSelect = findViewById((R.id.CourseSelect));

        CollectionReference CourseRef = firestore.collection("Courses");
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        courseID = document.getString("CourseID");
                        courseName = document.getString("CourseName");
                        instructor = document.getString("Instructor");

                        if (instructor.equals("")) {
                            line = courseID + ", " + courseName + ", Open";

                        } else {
                            line = courseID + ", " + courseName + ", Closed";

                        }
                        if (line == null){
                            Log.e("Null","Error");
                        }
                        Courses.add(line);
                    }

                }
                adapter = new ArrayAdapter(InstructorAssignmentActivity.this, android.R.layout.simple_list_item_1, Courses);
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

                                if (instructor.equals("") && courseID.equals(Course)) {
                                    firestore.collection("Courses").document(document.getId()).update("Instructor", username);
                                    Toast.makeText(InstructorAssignmentActivity.this, courseID +", "+ courseName+"Assigned", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(InstructorAssignmentActivity.this, InstructorActivity.class);
                                    intent.putExtra("username",username);
                                    startActivity(intent);
                                    finish();
                                }else if(instructor.equals("") && courseName.equals(Course)) {
                                    firestore.collection("Courses").document(document.getId()).update("Instructor", username);
                                    Toast.makeText(InstructorAssignmentActivity.this, courseID + ", " + courseName + "Assigned", Toast.LENGTH_SHORT).show();
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


    }
}