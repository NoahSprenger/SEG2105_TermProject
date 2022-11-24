package SEGTermProject.G15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StudentActivity extends AppCompatActivity {
    private Button btnLogOut, btnSearch, btnCourses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        btnSearch = findViewById(R.id.btnSearch);
        btnCourses = findViewById(R.id.btnCourses);
        btnLogOut = findViewById(R.id.btnLogOut);
        String username = getIntent().getStringExtra("username");

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, StudentEnrolActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });

        btnCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this, StudentCoursesActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });
    }
}