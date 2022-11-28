package SEGTermProject.G15;

import static com.google.firebase.firestore.FieldValue.arrayUnion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentEnrolActivity extends AppCompatActivity {

    ListView courseList;
    SimpleAdapter adapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String courseID, courseName, instructor, courseDescription, courseCapacity, courseDate1, courseDate2, Item;
    EditText CourseSelect, CourseDay;
    Button btnSearch, btnEnrol, btnBack;
    String[] CourseList = new String[4];
    ArrayList<String[]> ScheduleList = new ArrayList<String[]>();
    DBHandler db = new DBHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enrol);
        String username = getIntent().getStringExtra("username");
        courseList = (ListView) findViewById(R.id.CourseList);
        btnEnrol = findViewById(R.id.btnEnrol);
        btnSearch = findViewById(R.id.btnSearch);
        btnBack = findViewById(R.id.btnBack);
        CourseSelect = findViewById(R.id.CourseSelect);
        CourseDay = findViewById(R.id.CourseDay);

        CollectionReference UserRef = firestore.collection("Users");
        UserRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        if (document.getString("Username").equals(username)) {
                            Map<String, ArrayList> Courses = (Map<String, ArrayList>) document.get("Courses");
                            for (Map.Entry<String, ArrayList> value : Courses.entrySet()){
                                Object[] C = value.getValue().toArray();
                                CourseList[0] = (C[0].toString());
                                CourseList[1] = (C[1].toString());
                                CourseList[2] = (C[2].toString());
                                CourseList[3] = (C[3].toString());
                                ScheduleList.add(CourseList);
                            }
                        }

                    }
                }
            }
        });

        List<HashMap<String, String>> results = new ArrayList<>();
        adapter = new SimpleAdapter(StudentEnrolActivity.this, results, R.layout.custom_row_view_student,
                new String[]{"line1", "line2", "line3", "line4", "line5", "line6", "line7"},
                new int[]{R.id.courseID, R.id.courseName, R.id.instructName,
                        R.id.courseDescription, R.id.courseDate1, R.id.courseDate2, R.id.courseCapacity});

        CollectionReference CourseRef = firestore.collection("Courses");
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                   for (QueryDocumentSnapshot document : task.getResult()){
                       courseID = document.getString("CourseID");
                       courseName = document.getString("CourseName");
                       instructor = document.getString("Instructor");
                       courseDescription = document.getString("Description");
                       courseCapacity = document.getString("StudentCapacity");
                       courseDate1 = document.getString("Day1") + ", " + document.getString("Day1Hours");
                       courseDate2 = document.getString("Day2") + ", " + document.getString("Day2Hours");
                       HashMap<String, String> courseValues = new HashMap<>();
                       courseValues.put("line1", courseID);
                       courseValues.put("line2", courseName);
                       courseValues.put("line3", instructor);
                       courseValues.put("line4", courseDescription);
                       courseValues.put("line5",  ((ArrayList)document.get("Students")).size()+" / " + courseCapacity );
                       courseValues.put("line6", courseDate1);
                       courseValues.put("line7", courseDate2);
                       results.add(courseValues);
                   }
                }
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                courseList.setAdapter(adapter);
            }
        });

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                  HashMap SelectedItem = (HashMap) courseList.getItemAtPosition(i);
                  Item = SelectedItem.get("line1").toString();
                  Toast.makeText(StudentEnrolActivity.this, "Selected" + Item, Toast.LENGTH_SHORT).show();

              }
          });

                btnEnrol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Item == null) {
                            CourseSelect.setError("Select a course course");
                            Toast.makeText(StudentEnrolActivity.this, "No course selected", Toast.LENGTH_SHORT).show();
                        } else {
                            String Course = Item;
                            CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (document.getString("CourseID").equals(Item)) {
                                                courseID = document.getString("CourseID");
                                                courseName = document.getString("CourseName");
                                                instructor = document.getString("Instructor");
                                                courseDate1 = document.getString("Day1") + ", " + document.getString("Day1Hours");
                                                courseDate2 = document.getString("Day2") + ", " + document.getString("Day2Hours");

                                                Boolean FitsSchedule = true;
                                                for (int i = 0; i < ScheduleList.size(); i++) {
                                                    String[] temp = ScheduleList.get(i);
                                                    if (temp[0].equals(document.getString("Day1")) && temp[1].equals(document.getString("Day1Hours")) ||
                                                            temp[2].equals(document.getString("Day2")) && temp[3].equals(document.getString("Day2Hours"))) {
                                                        FitsSchedule = false;
                                                        CourseSelect.setError("Select a course with an unoccupied time slot");
                                                        Toast.makeText(StudentEnrolActivity.this, "Selected course is time slot in occupied", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                if (String.valueOf(((ArrayList)document.get("Students")).size()).equals(document.getString("StudentCapacity"))){
                                                    CourseSelect.setError("This course is full");
                                                }

                                                else if (FitsSchedule && (!instructor.equals("") && courseID.equals(Item)) || (!instructor.equals("") && courseName.equals(Item))) {
                                                    firestore.collection("Courses").document(
                                                            document.getId()).update("Students", FieldValue.arrayUnion(username));
                                                    UserRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                for (QueryDocumentSnapshot Userdocument : task.getResult()) {
                                                                    if (Userdocument.getString("Username").equals(username)) {
                                                                        Log.e("USER", Userdocument.getString("Username"));
                                                                        Map Schedule = (Map) Userdocument.get("Courses");
                                                                        String CourseID = document.getString("CourseID");
                                                                        ArrayList Days = new ArrayList<>();
                                                                        Days.add(document.getString("Day1"));
                                                                        Days.add(document.getString("Day1Hours"));
                                                                        Days.add(document.getString("Day2"));
                                                                        Days.add(document.getString("Day2Hours"));
                                                                        Schedule.put(CourseID, Days);
                                                                        firestore.collection("Users").document(Userdocument.getId()).update("Courses", Schedule);}
                                                                }
                                                            }
                                                        }
                                                    });
                                                    Toast.makeText(StudentEnrolActivity.this, courseID + ", " + courseName + "Enrolled", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(StudentEnrolActivity.this, StudentActivity.class);
                                                    intent.putExtra("username", username);
                                                    startActivity(intent);
                                                    finish();
                                                } else if ((instructor.equals("") && courseID.equals(Course)) || (instructor.equals("") && courseName.equals(Course))) {
                                                    CourseSelect.setError("Select a course with an instructor");
                                                    Toast.makeText(StudentEnrolActivity.this, "Selected course is closed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }
                });

                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(StudentEnrolActivity.this, StudentActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();

                    }
                });
    }
}