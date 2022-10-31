package SEGTermProject.G15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {
    Button btnUserUtilities, btnCourseUtilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler db = new DBHandler();
        setContentView(R.layout.activity_admin);
        btnUserUtilities = findViewById(R.id.btnUserUtilities);
        btnCourseUtilities = findViewById(R.id.btnCourseUtilities);
        btnCourseUtilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AdminCourseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnUserUtilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AdminUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}