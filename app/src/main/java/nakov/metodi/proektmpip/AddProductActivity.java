package nakov.metodi.proektmpip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AddProductActivity extends AppCompatActivity {

    private static final int CHOICE_IMG = 1;

    private Button choiceImage, btnUpload;
    private TextView view;
    private ImageView imgPreview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }
}
