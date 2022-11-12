package SEGTermProject.G15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstructorActivity extends User {
    private Button btnLogOut,btnView, btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnSearch = findViewById(R.id.btnSearch);
        btnView = findViewById(R.id.btnView);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorActivity.this, InstructorView.class);
                startActivity(intent);
                finish();
            }
        });


    }
}