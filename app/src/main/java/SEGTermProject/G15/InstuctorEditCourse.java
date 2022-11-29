package SEGTermProject.G15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Stack;

/**
 * Activity class for instructor course utilities.
 * Extends AppCompactActivity.
 */
public class InstuctorEditCourse extends AppCompatActivity {

    private Button btnEdit, btnLeave, btnBack;
    private Spinner courseSpin, day1Spin, day2Spin, hours1Spin, hours2Spin;
    private EditText studentCapasity, description;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Stack courseStack, day1Stack, day2Stack, hours2Stack, hours1Stack;
    private String courseSelect, day1Select, day2Select, hours1Select, hours2Select, capacitySelect, descriptionSelect;;

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instuctor_edit_course);

        String username = getIntent().getStringExtra("username");
        btnEdit = findViewById(R.id.btnEdit);
        btnLeave = findViewById(R.id.btnLeave);
        btnBack = findViewById(R.id.btnBack);
        courseSpin = findViewById(R.id.spinnerCourse);
        day1Spin = findViewById(R.id.spinnerDay1);
        day2Spin = findViewById(R.id.spinnerDay2);
        hours1Spin = findViewById(R.id.spinnerHours1);
        hours2Spin = findViewById(R.id.spinnerHours2);
        studentCapasity = findViewById(R.id.textStudentCapacity);
        description = findViewById(R.id.textDescription);

        courseStack = new Stack<>();
        courseStack.add("Select Course");


        CollectionReference CourseRef = firestore.collection("Courses");
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String courseID = document.getString("CourseID");
                        String courseName = document.getString("CourseName");
                        String instructor = document.getString("Instructor");

                        if (instructor.equals(username)) {
                            String line = courseID + ", " + courseName;
                            courseStack.add(line);

                        }
                    }
                }
                ArrayAdapter courseAdapter = new ArrayAdapter(InstuctorEditCourse.this, android.R.layout.simple_list_item_1, courseStack);
                courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseSpin.setAdapter(courseAdapter);
            }
        });

        day1Stack = new Stack<>();
        day2Stack = new Stack<>();
        day1Stack.add("Select first day");
        day2Stack.add("Select second day");
        String[] days = new String[]{"Monday","Tuesday", "Wednesday", "Thursday", "Friday"};
        for (int i = 0; i<days.length; i++){
            day1Stack.add(days[i]);
            day2Stack.add(days[i]);
        }
        ArrayAdapter day1Adapter = new ArrayAdapter(InstuctorEditCourse.this, android.R.layout.simple_list_item_1, day1Stack);
        day1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day1Spin.setAdapter(day1Adapter);

        ArrayAdapter day2Adapter = new ArrayAdapter(InstuctorEditCourse.this, android.R.layout.simple_list_item_1, day2Stack);
        day2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day2Spin.setAdapter(day2Adapter);

        hours1Stack = new Stack<>();
        hours2Stack = new Stack<>();
        hours1Stack.add("Select first day's hours");
        hours2Stack.add("Select second day's hours");
        String[] hours = new String[]{"8:30AM - 9:50AM","10:00AM - 11:20AM", "11:30AM - 12:50PM", "1:00PM - 2:20PM", "2:30PM - 3:40PM", "4:00PM - 5:20PM", "5:30PM - 6:50PM", "7:00PM - 8:20PM", "8:30PM - 9:50PM"};
        for (int i = 0; i<hours.length; i++){
            hours2Stack.add(hours[i]);
            hours1Stack.add(hours[i]);
        }
        ArrayAdapter hours1Adapter = new ArrayAdapter(InstuctorEditCourse.this, android.R.layout.simple_list_item_1, hours1Stack);
        hours1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hours1Spin.setAdapter(hours1Adapter);

        ArrayAdapter hours2Adapter = new ArrayAdapter(InstuctorEditCourse.this, android.R.layout.simple_list_item_1, hours2Stack);
        hours2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hours2Spin.setAdapter(hours2Adapter);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseSelect = courseSpin.getSelectedItem().toString();
                day1Select = day1Spin.getSelectedItem().toString();
                day2Select = day2Spin.getSelectedItem().toString();
                hours1Select = hours1Spin.getSelectedItem().toString();
                hours2Select = hours2Spin.getSelectedItem().toString();
                capacitySelect = studentCapasity.getText().toString();
                descriptionSelect = description.getText().toString();

                if(courseSelect.equals("Select Course")){
                    ((TextView)courseSpin.getSelectedView()).setError("Course not selected");
                }else if(day1Select.equals("Select first day")){
                    ((TextView)day1Spin.getSelectedView()).setError("Day not selected");
                }else if(day2Select.equals("Select second day")){
                    ((TextView)day1Spin.getSelectedView()).setError("Day not selected");
                }else if(hours1Select.equals("Select first day's hours")){
                    ((TextView)hours1Spin.getSelectedView()).setError("Hours not selected");
                }else if(hours1Select.equals("Select second day's hours")){
                    ((TextView)hours1Spin.getSelectedView()).setError("Hours not selected");
                }else if (day2Select.equals(day1Select) && hours1Select.equals(hours2Select)) {
                    ((TextView)day1Spin.getSelectedView()).setError("Both periods can't be at the same time");
                    ((TextView)day2Spin.getSelectedView()).setError("Both periods can't be at the same time");
                    ((TextView)hours1Spin.getSelectedView()).setError("Both periods can't be at the same time");
                    ((TextView)hours2Spin.getSelectedView()).setError("Both periods can't be at the same time");
                }else if (TextUtils.isEmpty(capacitySelect) || !capacitySelect.matches("-?(0|[1-9]\\d*)")){
                    studentCapasity.setError("Enter student capacity");
                }else if (TextUtils.isEmpty(descriptionSelect)){
                    description.setError("Enter class description");
                }else{
                    String courseCode = courseSelect.split(",")[0];
                    CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String courseID = document.getString("CourseID");
                                    String courseName = document.getString("CourseName");
                                    String instructor = document.getString("Instructor");

                                    if (courseID.equals(courseCode)) {
                                        firestore.collection("Courses").document(document.getId()).update("Day1", day1Select);
                                        firestore.collection("Courses").document(document.getId()).update("Day2", day2Select);
                                        firestore.collection("Courses").document(document.getId()).update("Day1Hours", hours1Select);
                                        firestore.collection("Courses").document(document.getId()).update("Day2Hours", hours2Select);
                                        firestore.collection("Courses").document(document.getId()).update("StudentCapacity", capacitySelect);
                                        firestore.collection("Courses").document(document.getId()).update("Description", descriptionSelect);
                                        Toast.makeText(InstuctorEditCourse.this, courseID + ", " + courseName + "Updated", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(InstuctorEditCourse.this, InstructorActivity.class);
                                        intent.putExtra("username", username);
                                        startActivity(intent);
                                        finish();


                                    }
                                }

                            }

                        }
                    });
                }

            }
        });

        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseSelect = courseSpin.getSelectedItem().toString();
                if(courseSelect.equals("Select Course")) {
                    ((TextView) courseSpin.getSelectedView()).setError("Course not selected");
                }else{
                    String courseCode = courseSelect.split(",")[0];
                    CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String courseID = document.getString("CourseID");
                                    String courseName = document.getString("CourseName");
                                    String instructor = document.getString("Instructor");

                                    if (courseID.equals(courseCode)) {
                                        firestore.collection("Courses").document(document.getId()).update("Instructor", "");
                                        firestore.collection("Courses").document(document.getId()).update("Day1", "");
                                        firestore.collection("Courses").document(document.getId()).update("Day2", "");
                                        firestore.collection("Courses").document(document.getId()).update("Day1Hours", "");
                                        firestore.collection("Courses").document(document.getId()).update("Day2Hours", "");
                                        firestore.collection("Courses").document(document.getId()).update("StudentCapacity", "");
                                        firestore.collection("Courses").document(document.getId()).update("Description", "");
                                        Toast.makeText(InstuctorEditCourse.this, "Left"+courseID + ", " + courseName , Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(InstuctorEditCourse.this, InstructorActivity.class);
                                        intent.putExtra("username", username);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                            }

                        }
                    });
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstuctorEditCourse.this, InstructorActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}