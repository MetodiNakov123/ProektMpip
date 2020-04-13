package nakov.metodi.proektmpip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView emailText;
    Button btnSignOut;
    Button btnAddProduct;

    ViewFlipper imgBanner;

    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;

    private DatabaseReference mDatabaseRef;
    private List<Product> mProducts;

    private ProgressBar mProgressCircle;  //Added ProgressBar showProducts

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        emailText = findViewById(R.id.textEmail);
        btnSignOut = findViewById(R.id.btn_signOut);
        btnAddProduct = findViewById(R.id.btn_addProduct);

        mProgressCircle = findViewById(R.id.progress_circle);

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


        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, AddProductActivity.class);
                startActivity(intent);
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

        showProducts();



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


    public void showProducts(){
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mProducts = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Product product = postSnapshot.getValue(Product.class);
                    mProducts.add(product);
                }

                mAdapter = new ProductAdapter(ShopActivity.this, mProducts);
                mRecyclerView.setAdapter(mAdapter);

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

//            mProducts.add(new Product("gs://mpipproekt.appspot.com/popular/televizor.jpg", "$300", "TV"));
//            mAdapter = new ProductAdapter(ShopActivity.this, mProducts);
//            mRecyclerView.setAdapter(mAdapter);
//
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShopActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

}
