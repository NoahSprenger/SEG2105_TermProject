package SEGTermProject.G15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCourseActivity extends AppCompatActivity {
    Button btnEditCourse, btnDeleteCourse, btnCreateCourse;
    EditText inputCourse, inputCode, inputNewCode, inputNewCourse;
    String CourseName, CourseCode, NewCourseName, NewCourseCode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler db = new DBHandler();
        setContentView(R.layout.activity_admin_course);
        btnEditCourse = findViewById(R.id.btnAdminEditCourse);
        btnCreateCourse = findViewById(R.id.btnAdminCreateCourse);
        btnDeleteCourse = findViewById(R.id.btnAdminDeleteCourse);

        inputCode = findViewById(R.id.courseCode);
        inputCourse = findViewById(R.id.courseName);
        inputNewCode = findViewById(R.id.newCourseCode);
        inputNewCourse = findViewById(R.id.newCourseName);

        btnEditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseCode = inputCode.getText().toString();
                CourseName = inputCourse.getText().toString();
                NewCourseCode = inputNewCode.getText().toString();
                NewCourseName = inputNewCourse.getText().toString();
                db.editCourse(CourseCode, NewCourseCode, NewCourseName);
                Intent intent = new Intent(AdminCourseActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewCourseCode = inputNewCode.getText().toString();
                NewCourseName = inputNewCourse.getText().toString();
                db.addCourse(NewCourseCode, NewCourseName);
                Intent intent = new Intent(AdminCourseActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseCode = inputCode.getText().toString();
                db.deleteCourse(CourseCode);
                Intent intent = new Intent(AdminCourseActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
