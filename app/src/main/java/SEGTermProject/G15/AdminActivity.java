package SEGTermProject.G15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Activity class for the admin.
 * Extends AppCompactActivity.
 */
public class AdminActivity extends AppCompatActivity {

    private Button btnUserUtilities, btnCourseUtilities, btnLogOut;

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler db = new DBHandler();
        setContentView(R.layout.activity_admin);
        btnUserUtilities = findViewById(R.id.btnUserUtilities);
        btnCourseUtilities = findViewById(R.id.btnCourseUtilities);
        btnLogOut = findViewById(R.id.btnLogOut);
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
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}