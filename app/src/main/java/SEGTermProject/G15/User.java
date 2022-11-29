package SEGTermProject.G15;

import androidx.appcompat.app.AppCompatActivity;

/**
 * User class for representing a user.
 * Extends AppCompactActivity.
 */
public class User extends AppCompatActivity {
    private String userName;
    private String password;
    private String Email;
    private String Type;

    /**
     * Getter for the user's type.
     * @return the user's type
     */
    public String getType() {
        return Type;
    }

    /**
     * Setter for the user's type.
     * @param type the user's type
     */
    public void setType(String type) {
        this.Type = type;
    }

    /**
     * Getter for the user's username.
     * @return the user's username
     */
    public String getUsername() {
        return userName;
    }

    /**
     * Setter for the user's username.
     * @param userName the user's username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for the user's password.
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the user's password.
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the user's email.
     * @return the user's email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Setter for the user's email.
     * @param email the user's email
     */
    public void setEmail(String email) {
        this.Email = email;
    }
}
