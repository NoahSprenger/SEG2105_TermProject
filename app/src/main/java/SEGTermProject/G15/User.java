package SEGTermProject.G15;

import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {
    private String userName;
    private String password;
    private String Email;
    private String Type;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }


    public String getUsername() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }
}
