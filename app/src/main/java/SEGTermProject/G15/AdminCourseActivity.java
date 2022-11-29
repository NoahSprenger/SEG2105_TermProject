package SEGTermProject.G15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Activity class for admin course utilities.
 * Extends AppCompactActivity.
 */
public class AdminCourseActivity extends AppCompatActivity {
    private Button btnEditCourse, btnDeleteCourse, btnCreateCourse;
    private EditText inputNewCode, inputNewCourse;
    private String NewCourseName, NewCourseCode, inputCode;
    private Spinner mySpinner;

    /**
     * Method for when the activity is created.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler db = new DBHandler();

        setContentView(R.layout.activity_admin_course);
        btnEditCourse = findViewById(R.id.btnAdminEditCourse);
        btnCreateCourse = findViewById(R.id.btnAdminCreateCourse);
        btnDeleteCourse = findViewById(R.id.btnAdminDeleteCourse);

        inputNewCode = findViewById(R.id.newCourseCode);
        inputNewCourse = findViewById(R.id.newCourseName);
        mySpinner = findViewById(R.id.spinner);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        Stack<String> Courses = new Stack<String>();
        Courses.add("Select CourseID");
        CollectionReference CourseRef = firestore.collection("Courses");
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String course = document.getString("CourseID");
                        Courses.add(course);
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(AdminCourseActivity.this, android.R.layout.simple_list_item_1, Courses);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner.setAdapter(arrayAdapter);
                }
            }
        });

        btnEditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputCode = mySpinner.getSelectedItem().toString();
                NewCourseCode = inputNewCode.getText().toString();
                NewCourseName = inputNewCourse.getText().toString();
                if (inputCode.equals("Select CourseID")){
                    inputNewCode.setError("No course ID selected");
                    Toast.makeText(AdminCourseActivity.this, "Please select course ID", Toast.LENGTH_SHORT).show();
                }else {
                    db.editCourse(inputCode, NewCourseCode, NewCourseName);
                    Toast.makeText(AdminCourseActivity.this, inputCode + " has been edited to, CourseId: "+NewCourseCode+" and Course name: "+NewCourseName, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminCourseActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewCourseCode = inputNewCode.getText().toString();
                NewCourseName = inputNewCourse.getText().toString();
                db.addCourse(NewCourseCode, NewCourseName);
                Toast.makeText(AdminCourseActivity.this, "Course "+NewCourseCode+", "+NewCourseName+" has been added.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminCourseActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputCode = mySpinner.getSelectedItem().toString();
                if (inputCode.equals("Select CourseID")){
                    inputNewCode.setError("No course ID selected");
                    Toast.makeText(AdminCourseActivity.this, "Please select course ID", Toast.LENGTH_SHORT).show();
                }else {
                    db.deleteCourse(inputCode);
                    Toast.makeText(AdminCourseActivity.this, "Course "+inputCode+" has been deleted.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdminCourseActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
