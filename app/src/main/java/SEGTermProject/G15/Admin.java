package SEGTermProject.G15;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Class for representing an admin user. 
 * Extends User class.
 */
public class Admin extends User {

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }


}