package SEGTermProject.G15;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity {

    EditText Username, Password;
    Button signupBtn;

    signup(){
    }

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.signup);


        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);

        signupBtn = findViewById(R.id.signupBtn);

        DBHandler db = new DBHandler();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = Username.getText().toString();
                String password = Password.getText().toString();

                db.addUser("Student", userName, password);
            }
        });



    }
}
