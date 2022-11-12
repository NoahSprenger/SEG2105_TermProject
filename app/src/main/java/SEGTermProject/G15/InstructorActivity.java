package SEGTermProject.G15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstructorActivity extends AppCompatActivity {
    private Button btnLogOut, btnAssign, btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnAssign = findViewById(R.id.btnAssign);
        btnEdit = findViewById(R.id.btnEdit);
        String username = getIntent().getStringExtra("username");

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorActivity.this, InstructorAssignmentActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorActivity.this, InstuctorEditCourse.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });
    }
}