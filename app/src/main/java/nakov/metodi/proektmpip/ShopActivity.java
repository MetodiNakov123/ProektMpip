package nakov.metodi.proektmpip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;

public class ShopActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView emailText;
    Button btnSignOut;

    ViewFlipper imgBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        emailText = findViewById(R.id.textEmail);
        btnSignOut = findViewById(R.id.btn_signOut);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            String EMAIL = mAuth.getCurrentUser().getEmail();
            int posET = EMAIL.indexOf("@");
            emailText.setText("Здраво, " + EMAIL.substring(0, posET));

        }

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut(); //End user session
                startActivity(new Intent(ShopActivity.this, MainActivity.class)); //Go back to home page
                finish();
            }
        });


        imgBanner = findViewById(R.id.imgBanner);
        int sliders[] = {
                R.drawable.banner1, R.drawable.banner2, R.drawable.banner3
        };

        for (int slide : sliders) {
            bannerFliper(slide);
        }
    }

    public void bannerFliper(int image){
         ImageView imageView = new ImageView(this);
         imageView.setImageResource(image);
         imgBanner.addView(imageView);
         imgBanner.setFlipInterval(5000);
         imgBanner.setAutoStart(true);
         imgBanner.setInAnimation(this, android.R.anim.fade_in);
         imgBanner.setOutAnimation(this, android.R.anim.fade_out);
    }



}
