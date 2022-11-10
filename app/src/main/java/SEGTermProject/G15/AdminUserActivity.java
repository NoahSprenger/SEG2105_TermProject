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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Stack;

public class AdminUserActivity extends AppCompatActivity {
    private Button btnDeleteStudent, btnDeleteInstructor;
    private String InstructorName, StudentName;
    private Spinner InstuctorSpinner, StudentSpinner;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler db = new DBHandler();
        setContentView(R.layout.activity_admin_user);
        btnDeleteInstructor = findViewById(R.id.btnAdminDeleteInstructor);
        btnDeleteStudent = findViewById(R.id.btnAdminDeleteStudent);
        StudentSpinner = findViewById(R.id.spinnerStudent);
        InstuctorSpinner = findViewById(R.id.spinnerInstructor);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Stack<String> Students = new Stack<String>();
        Stack<String> Instructors = new Stack<String>();
        Students.add("Select Student");
        Instructors.add("Select Instructor");
        CollectionReference UserRef = firestore.collection("Users");
        UserRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String Type = document.getString("Type");
                        if (Type.equals("Student")) {
                            Students.add(document.getString("Username"));
                        }else if(Type.equals("Instructor")) {
                            Instructors.add(document.getString("Username"));
                        }
                    }
                    ArrayAdapter StudentAdapter = new ArrayAdapter(AdminUserActivity.this, android.R.layout.simple_list_item_1, Students);
                    ArrayAdapter InstructorAdapter = new ArrayAdapter(AdminUserActivity.this, android.R.layout.simple_list_item_1, Instructors);
                    StudentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    InstructorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    StudentSpinner.setAdapter(StudentAdapter);
                    InstuctorSpinner.setAdapter(InstructorAdapter);
                }
            }
        });
        btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentName = StudentSpinner.getSelectedItem().toString();
                if(StudentName.equals("Select Student")){
                    btnDeleteStudent.setError("Select Student");
                    Toast.makeText(AdminUserActivity.this, "Please select a student", Toast.LENGTH_SHORT).show();
                }
                db.deleteUser(StudentName);
                Toast.makeText(AdminUserActivity.this, "Student "+StudentName+" has been deleted.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminUserActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDeleteInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstructorName = InstuctorSpinner.getSelectedItem().toString();
                if(InstructorName.equals("Select Instructor")){
                    btnDeleteStudent.setError("Select Instructor");
                    Toast.makeText(AdminUserActivity.this, "Please select an instructor", Toast.LENGTH_SHORT).show();
                }
                db.deleteUser(InstructorName);
                Toast.makeText(AdminUserActivity.this, "Instructor "+InstructorName+" has been deleted.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminUserActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
