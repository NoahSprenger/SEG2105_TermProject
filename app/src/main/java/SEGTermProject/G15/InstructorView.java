package SEGTermProject.G15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.Stack;

public class InstructorView extends AppCompatActivity {

    ListView lvCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler db = new DBHandler();
        setContentView(R.layout.activity_instructor_view);

        lvCourses = findViewById(R.id.lvCourses);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        Stack<String> courses = new Stack<String>();
        CollectionReference courseRef = firestore.collection("Courses");
        courseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String course = document.getString("CourseID");
                        courses.add(course);
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(InstructorView.this, android.R.layout.simple_list_item_1, courses);
                    lvCourses.setAdapter(arrayAdapter);
                }
            }
        });
        lvCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(InstructorView.this, InstructorCourseEdt.class);
                startActivity(intent);
                finish();
            }
        });
    }
}