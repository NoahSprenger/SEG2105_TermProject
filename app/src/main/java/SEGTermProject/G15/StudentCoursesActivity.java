package SEGTermProject.G15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class StudentCoursesActivity extends AppCompatActivity {

    ListView courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_courses);
        String username = getIntent().getStringExtra("username");
    }
}