package SEGTermProject.G15;

import static org.junit.Assert.assertTrue;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;

import com.google.android.gms.common.internal.FallbackServiceBroker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * local unit test, which will execute on the development machine (host).
 *
 */
@RunWith(AndroidJUnit4.class)
public class UnitTest {



//    @Test
//    public void test_add_course() {
//        DBHandler db = new DBHandler();
//
//        db.addCourse("GNG101", "Engineering introduction");
//        Stack<String> courses = db.getCourses();
//        assertTrue(courses.search("GNG101") != -1);
//    }
//    @Test
//    public void test_add_student() {
//        DBHandler db = new DBHandler();
//
//        Student test_student = new Student();
//        test_student.setEmail("testEmail@gmail.com");
//        test_student.setUserName("TestStudent");
//        test_student.setPassword("123Student");
//        test_student.setType("Student");
//        try {
//            db.addUser(test_student);
//        } catch (Exception e) {
//            System.out.println("Invalid User");
//        }
//        Stack<String> users = db.getUsers();
//        assertTrue(users.search("TestStudent") != -1);
//    }
//    @Test
//    public void test_add_Instructor() {
//        DBHandler db = new DBHandler();
//
//        Instructor test_instructor = new Instructor();
//        test_instructor.setEmail("testInstructor@gmail.com");
//        test_instructor.setUserName("testInstructor");
//        test_instructor.setPassword("123Instructor");
//        test_instructor.setType("Instructor");
//        try {
//            db.addUser(test_instructor);
//        } catch (Exception e) {
//            System.out.println("Invalid User");
//        }        Stack<String> users = db.getUsers();
//        assertTrue(users.search("testInstructor") != -1);
//    }
//    @Test
//    public void test_delete_course() {
//        DBHandler db = new DBHandler();
//
//        db.deleteCourse("GNG101");
//        Stack<String> courses = db.getCourses();
//        assertTrue(courses.search("GNG101") == -1);
//    }
//    @Test
//    public void test_delete_student() {
//        DBHandler db = new DBHandler();
//
//        db.deleteUser("TestStudent");
//        Stack<String> users = db.getUsers();
//        assertTrue(users.search("testEmail@gmail.com") == -1);
//    }
//    @Test
//    public void test_delete_Instructor() {
//        DBHandler db = new DBHandler();
//
//        db.deleteUser("testInstructor");
//        Stack<String> users = db.getUsers();
//        assertTrue(users.search("testInstructor@gmail.com") == -1);
//    }

    private void Enroll() {
        DBHandler db = new DBHandler();

        ArrayList<String[]> Schedule = new ArrayList<>();
        db.EnrollStudent("JUnitStudent", "MAT2023", Schedule);
    }

    private void Unenroll() {
        DBHandler db = new DBHandler();
        db.UnenrollStudent("JUnitStudent", "MAT2023");
    }

    @Test
    public void testEnrollUserSide() {
        Enroll();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference UserRef = firestore.collection("Users");
        UserRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("Username").equals("JUnitStudent")) {
                            Map<String, ArrayList> Courses = (Map<String, ArrayList>) document.get("Courses");
                            for (Map.Entry<String, ArrayList> value : Courses.entrySet()) {
                                if (value.getKey().equals("MAT023")) {
                                    assertTrue(true);
                                }
                            }
                            assertTrue(false);
                        }
                    }
                }
            }
        });
    }

    public void testEnrollScheduleConflict() {
        DBHandler db = new DBHandler();
        ArrayList<String[]> Schedule = new ArrayList<>();
        Schedule.add(new String[]{"Monday", "8:30AM - 9:50AM", "Wednesday", "8:30PM - 9:50PM"});
        db.EnrollStudent("JUnitStudent", "MAT2023", Schedule);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference UserRef = firestore.collection("Users");
        UserRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("Username").equals("JUnitStudent")) {
                            Map<String, ArrayList> Courses = (Map<String, ArrayList>) document.get("Courses");
                            for (Map.Entry<String, ArrayList> value : Courses.entrySet()) {
                                if (value.getKey().equals("MAT023")) {
                                    assertTrue(true);
                                }
                            }
                            assertTrue(false);
                        }
                    }
                }
            }
        });
    }

    @Test
    public void testUnenrollUserSide() {
        Unenroll();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference UserRef = firestore.collection("Users");
        UserRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("Username").equals("JUnitStudent")) {
                            Map<String, ArrayList> Courses = (Map<String, ArrayList>) document.get("Courses");
                            for (Map.Entry<String, ArrayList> value : Courses.entrySet()) {
                                if (value.getKey().equals("MAT023")) {
                                    assertTrue(false);
                                }
                            }
                            assertTrue(true);
                        }
                    }
                }
            }
        });
    }

    @Test
    public void testEnrollCouseSide () {
        Enroll();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference CourseRef = firestore.collection("Users");
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("CourseID").equals("MAT2023")) {
                            ArrayList<String> Students = (ArrayList) document.get("Students");

                            for (int i = 0; i < Students.size(); i++) {
                                if (Students.get(i).equals("JUnitStudent")) {
                                    assertTrue(true);
                                }
                                assertTrue(false);
                            }
                        }
                    }
                }
            }
        });
    }

    @Test
    public void testUnenrollCourseSide() {
        Unenroll();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference CourseRef = firestore.collection("Users");
        CourseRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("CourseID").equals("MAT2023")) {
                            ArrayList<String> Students = (ArrayList) document.get("Students");

                            for (int i = 0; i < Students.size(); i++) {
                                if (Students.get(i).equals("JUnitStudent")) {
                                    assertTrue(false);
                                }
                            }
                            assertTrue(true);

                        }
                    }
                }
            }
        });
    }


    
}