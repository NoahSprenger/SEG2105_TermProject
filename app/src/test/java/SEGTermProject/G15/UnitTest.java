package SEGTermProject.G15;

import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

/**
 * local unit test, which will execute on the development machine (host).
 *
 */
public class UnitTest {
    @Test
    public void add_course() {
        DBHandler db = new DBHandler();
        db.addCourse("GNG101", "Engineering introduction");
        Stack<String> courses = db.getCourses();
        assertTrue(courses.search("GNG101") != -1);
    }
    @Test
    public void add_student() {
        DBHandler db = new DBHandler();
        Student test_student = new Student();
        test_student.setEmail("testEmail@gmail.com");
        test_student.setUserName("TestStudent");
        test_student.setPassword("123Student");
        test_student.setType("Student");
        db.addUser(test_student);
        Stack<String> users = db.getUsers();
        assertTrue(users.search("testEmail@gmail.com") != -1);
    }
    @Test
    public void add_Instructor() {
        DBHandler db = new DBHandler();
        Student test_instructor = new Student();
        test_instructor.setEmail("testInstructor@gmail.com");
        test_instructor.setUserName("testInstructor");
        test_instructor.setPassword("123Instructor");
        test_instructor.setType("Instructor");
        db.addUser(test_instructor);
        Stack<String> users = db.getUsers();
        assertTrue(users.search("testInstructor@gmail.com") != -1);
    }
    @Test
    public void delete_course() {
        DBHandler db = new DBHandler();
        db.deleteCourse("GNG101");
        Stack<String> courses = db.getCourses();
        assertTrue(courses.search("GNG101") == -1);
    }
    @Test
    public void delete_student() {
        DBHandler db = new DBHandler();
        db.deleteUser("TestStudent");
        Stack<String> users = db.getUsers();
        assertTrue(users.search("testEmail@gmail.com") == -1);
    }
    @Test
    public void delete_Instructor() {
        DBHandler db = new DBHandler();
        db.deleteUser("testInstructor");
        Stack<String> users = db.getUsers();
        assertTrue(users.search("testInstructor@gmail.com") == -1);
    }
    /** Runs the tests. */
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("T");
    }
}