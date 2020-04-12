package nakov.metodi.proektmpip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class AddProductActivity extends AppCompatActivity {

    private static final int CHOICE_IMAGE = 1;

    private Button choiceImage, btnUpload;
    private TextView viewProducts;
    private ImageView imgPreview;
    private ProgressBar uploadProgress;

    private EditText prTitle, prPrice;
    private TextView txtError;

    private Uri imgUrl;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        choiceImage = findViewById(R.id.choiseImage);
        btnUpload = findViewById(R.id.btnUploadImage);
        viewProducts = findViewById(R.id.viewProducts);
        txtError = findViewById(R.id.txtError);
        prPrice = findViewById(R.id.prPrice);
        prTitle = findViewById(R.id.prTitle);
        imgPreview = findViewById(R.id.imgPreview);
        uploadProgress = findViewById(R.id.uploadProgress);


        mStorageRef = FirebaseStorage.getInstance().getReference("products");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");

        mAuth = FirebaseAuth.getInstance();



        choiceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChoice();
            }
        });





        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(AddProductActivity.this, "Upload in progress", Toast.LENGTH_LONG).show();
                }
                else {


                    String title = prTitle.getText().toString().trim();
                    String price = prPrice.getText().toString().trim();

                    if (title.equals("") || title == null) {
                        txtError.setText("Внеси име на продуктот!!!");
                        return;
                    }

                    if (price.equals("") || price == null) {
                        txtError.setText("Внеси цена на продуктот!!!");
                        return;
                    }

                    upload();

                }
            }
        });





        viewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });







    }


    private void showFileChoice(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOICE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==CHOICE_IMAGE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imgUrl = data.getData();
            Picasso.with(this)
                    .load(imgUrl)
                    .into(imgPreview);
        }
    }



    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }



    private void upload(){

        if(imgUrl != null){
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(imgUrl));

            mUploadTask = fileReference.putFile(imgUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgress.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(AddProductActivity.this, "Upload successfully", Toast.LENGTH_LONG).show();

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    String userEmail = mAuth.getCurrentUser().getEmail();
                                    Product product = new Product(url,prPrice.getText().toString().trim(), prTitle.getText().toString().trim(), userEmail);
                                    String uploadID = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadID).setValue(product);
                                }
                            });


                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddProductActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }



                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = ( 100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            uploadProgress.setProgress((int) progress);
                        }
                    });


            new Handler().postDelayed(new Runnable() {   // se povikuva aktiviti za 3.5 sec za da se dodade noviot product, da nema duplikati
                @Override
                public void run() {

                    Intent intent = new Intent(AddProductActivity.this, ShopActivity.class);
                    startActivity(intent);
                }
            }, 3500);



        }




        else{
            Toast.makeText(AddProductActivity.this, "No file selected", Toast.LENGTH_SHORT).show();
        }



    }


}
