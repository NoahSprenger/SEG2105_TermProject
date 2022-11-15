package SEGTermProject.G15;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;

import static org.junit.Assert.*;
/**
 * local unit test, which will execute on the development machine (host).
 *
 */
@RunWith(AndroidJUnit4.class)
public class UnitTest {
    @Test
    public void test_add_course() {
        DBHandler db = new DBHandler();

        db.addCourse("GNG101", "Engineering introduction");
        Stack<String> courses = db.getCourses();
        assertTrue(courses.search("GNG101") != -1);
    }
    @Test
    public void test_add_student() {
        DBHandler db = new DBHandler();

        Student test_student = new Student();
        test_student.setEmail("testEmail@gmail.com");
        test_student.setUserName("TestStudent");
        test_student.setPassword("123Student");
        test_student.setType("Student");
        try {
            db.addUser(test_student);
        } catch (Exception e) {
            System.out.println("Invalid User");
        }
        Stack<String> users = db.getUsers();
        assertTrue(users.search("TestStudent") != -1);
    }
    @Test
    public void test_add_Instructor() {
        DBHandler db = new DBHandler();

        Instructor test_instructor = new Instructor();
        test_instructor.setEmail("testInstructor@gmail.com");
        test_instructor.setUserName("testInstructor");
        test_instructor.setPassword("123Instructor");
        test_instructor.setType("Instructor");
        try {
            db.addUser(test_instructor);
        } catch (Exception e) {
            System.out.println("Invalid User");
        }        Stack<String> users = db.getUsers();
        assertTrue(users.search("testInstructor") != -1);
    }
    @Test
    public void test_delete_course() {
        DBHandler db = new DBHandler();

        db.deleteCourse("GNG101");
        Stack<String> courses = db.getCourses();
        assertTrue(courses.search("GNG101") == -1);
    }
    @Test
    public void test_delete_student() {
        DBHandler db = new DBHandler();

        db.deleteUser("TestStudent");
        Stack<String> users = db.getUsers();
        assertTrue(users.search("testEmail@gmail.com") == -1);
    }
    @Test
    public void test_delete_Instructor() {
        DBHandler db = new DBHandler();

        db.deleteUser("testInstructor");
        Stack<String> users = db.getUsers();
        assertTrue(users.search("testInstructor@gmail.com") == -1);
    }
}