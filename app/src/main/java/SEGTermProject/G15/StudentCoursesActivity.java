package SEGTermProject.G15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity class for student course utilities.
 * Extends AppCompactActivity.
 */
public class StudentCoursesActivity extends AppCompatActivity {

    ListView courseList;
    SimpleAdapter adapter;
    Button btnUnenrol, btnBack, btnSearch;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String Item;
    DBHandler db = new DBHandler();

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_courses);
        String username = getIntent().getStringExtra("username");

        courseList = (ListView) findViewById(R.id.CourseList);

        btnUnenrol = findViewById(R.id.btnUnenrol);
        btnBack = findViewById(R.id.btnBack);

        List<HashMap<String, String>> results = new ArrayList<>();
        adapter = new SimpleAdapter(StudentCoursesActivity.this, results, R.layout.custom_row_view_student,
                new String[]{"line1", "line2", "line3"},
                new int[]{R.id.courseID,R.id.courseDate1, R.id.courseDate2});

        CollectionReference UserRef = firestore.collection("Users");
        UserRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        if (document.getString("Username").equals(username)) {
                            Map<String, ArrayList> Courses = (Map<String, ArrayList>) document.get("Courses");
                            int i = 0;
                            for (Map.Entry<String, ArrayList> value : Courses.entrySet()){
                                HashMap<String, String> courseValues = new HashMap<>();
                                courseValues.put("line1",value.getKey());
                                Object[] C = value.getValue().toArray();
                                Log.e("Passed Here",String.valueOf(i));
                                i++;
                                courseValues.put("line2", C[0].toString()+" " +C[1].toString().toString());
                                courseValues.put("line3", C[2].toString()+" " +C[3].toString());
                                results.add(courseValues);
                            }
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            courseList.setAdapter(adapter);
                        }

                    }
                }
            }
        });

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap SelectedItem = (HashMap) courseList.getItemAtPosition(i);
                Item = SelectedItem.get("line1").toString();
                Toast.makeText(StudentCoursesActivity.this, "Selected" + Item, Toast.LENGTH_SHORT).show();

            }
        });

        btnUnenrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Item == null){
                    btnUnenrol.setError("Select a course");
                }else{
                    db.UnenrollStudent(username,Item);
                    Toast.makeText(StudentCoursesActivity.this, Item+ "Unenrolled", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StudentCoursesActivity.this, StudentActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();

//                    CollectionReference UserRef = firestore.collection("Users");
//                    UserRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()){
//                                for (QueryDocumentSnapshot document : task.getResult()){
//                                    if (document.getString("Username").equals(username)) {
//                                        Map<String, ArrayList> Courses = (Map<String, ArrayList>) document.get("Courses");
//                                        Courses.remove(Item);
//                                        firestore.collection("Users").document(document.getId()).update("Courses", Courses);
//                                        Toast.makeText(StudentCoursesActivity.this, Item+ "Unenrolled", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(StudentCoursesActivity.this, StudentActivity.class);
//                                        intent.putExtra("username", username);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//
//                                }
//                            }
//                        }
//                    });
//                    CollectionReference CourseRef = firestore.collection("Courses");
//                    CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if(task.isSuccessful()){
//                                for (QueryDocumentSnapshot document : task.getResult()){
//                                    if (document.getString("CourseID").equals(Item)){
//                                        ArrayList Students = (ArrayList) document.get("Students");
//                                        Students.remove(username);
//                                        firestore.collection("Courses").document(document.getId()).update("Students", Students);
//
//                                    }
//                                }
//                            }
//                        }
//                    });
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentCoursesActivity.this, StudentActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });

    }
}