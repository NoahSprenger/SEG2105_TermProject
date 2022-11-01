package SEGTermProject.G15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AdminUserActivity extends AppCompatActivity {
    private Button btnDeleteStudent, btnDeleteInstructor;
    private EditText InputInstructorName, InputStudentName;
    private String InstructorName, StudentName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler db = new DBHandler();
        setContentView(R.layout.activity_admin_user);
        btnDeleteInstructor = findViewById(R.id.btnAdminDeleteInstructor);
        btnDeleteStudent = findViewById(R.id.btnAdminDeleteStudent);
        InputInstructorName = findViewById(R.id.adminInstructorName);
        InputStudentName = findViewById(R.id.adminStudentName);
        btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentName = InputStudentName.getText().toString();
                db.deleteUser(StudentName);
                Intent intent = new Intent(AdminUserActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDeleteInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstructorName = InputInstructorName.getText().toString();
                db.deleteUser(InstructorName);
                Intent intent = new Intent(AdminUserActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
