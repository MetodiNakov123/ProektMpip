package nakov.metodi.proektmpip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ShopActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView emailText;
    Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        emailText = findViewById(R.id.textEmail);
        btnSignOut = findViewById(R.id.btn_signOut);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            String EMAIL= mAuth.getCurrentUser().getEmail();
            int posET = EMAIL.indexOf("@");
            emailText.setText("Здраво, " + EMAIL.substring(0,posET));

        }

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut(); //End user session
                startActivity(new Intent(ShopActivity.this, MainActivity.class)); //Go back to home page
                finish();
            }
        });




    }
}
