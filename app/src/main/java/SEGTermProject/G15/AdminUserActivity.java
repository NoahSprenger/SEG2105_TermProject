package SEGTermProject.G15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminUserActivity extends AppCompatActivity {
    private Button btnDeleteStudent, btnDeleteInstructor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler db = new DBHandler();
        setContentView(R.layout.activity_admin_user);
        btnDeleteInstructor = findViewById(R.id.btnAdminDeleteInstructor);
        btnDeleteStudent = findViewById(R.id.btnAdminDeleteStudent);
        btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminUserActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDeleteInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminUserActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
