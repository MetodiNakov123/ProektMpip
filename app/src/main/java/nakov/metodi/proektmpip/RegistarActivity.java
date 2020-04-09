package nakov.metodi.proektmpip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class RegistarActivity extends AppCompatActivity {


    EditText ufirstName;
    EditText ulastName;
    EditText uemail;
    EditText upassword;
    EditText uconfpassword;
    EditText ucontactNo;

    Button brnRegister;

    TextInputLayout firstNameWrapper, lastNameWrapper, emailWrapper, passwordWrapper, confPasswordWrapper, contactNoWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        ufirstName = findViewById(R.id.userFirstName);
        ulastName = findViewById(R.id.userLastName);
        uemail = findViewById(R.id.userEmailAddress);
        upassword = findViewById(R.id.userPassword);
        uconfpassword = findViewById(R.id.userConfPassword);
        ucontactNo = findViewById(R.id.userContactNumber);

        firstNameWrapper = findViewById(R.id.userFirstNameWrapper);
        lastNameWrapper = findViewById(R.id.lastNameWrapper);
        emailWrapper = findViewById(R.id.userEmailWrapper);
        passwordWrapper = findViewById(R.id.userPasswordWrapper);
        confPasswordWrapper = findViewById(R.id.confPasswordWrapper);
        contactNoWrapper = findViewById(R.id.contactNoWrapper);


        brnRegister = findViewById(R.id.btnRegister);

        brnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = ufirstName.getText().toString().trim();
                String lastName = ulastName.getText().toString().trim();
                String email = uemail.getText().toString().trim();
                String password = upassword.getText().toString().trim();
                String confpassword = uconfpassword.getText().toString().trim();
                String contactNo = ucontactNo.getText().toString().trim();

                //validation
                if(firstName.isEmpty()){
                    firstNameWrapper.setError("Внеси име!");
                    firstNameWrapper.requestFocus();
                    return;
                }

                if(lastName.isEmpty()){
                    lastNameWrapper.setError("Внеси презиме!");
                    lastNameWrapper.requestFocus();
                    return;
                }

                if(email.isEmpty()){
                    emailWrapper.setError("Внеси емаил!");
                    emailWrapper.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    passwordWrapper.setError("Внеси лозинка!");
                    passwordWrapper.requestFocus();
                    return;
                }

                if(confpassword.isEmpty()){
                    confPasswordWrapper.setError("Внеси ja лозинката повторно!");
                    confPasswordWrapper.requestFocus();
                    return;
                }

                if(!password.equals(confpassword)){
                    confPasswordWrapper.setError("Лозинката не се совпаѓа!");
                    confPasswordWrapper.requestFocus();
                }

                if(contactNo.isEmpty()){
                    contactNoWrapper.setError("Внеси телефон за контакт!");
                    contactNoWrapper.requestFocus();
                    return;
                }










            }
        });
    }
}
