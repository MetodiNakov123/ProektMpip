package nakov.metodi.proektmpip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistarActivity extends AppCompatActivity {


    EditText ufirstName;
    EditText ulastName;
    EditText uemail;
    EditText upassword;
    EditText uconfpassword;
    EditText ucontactNo;

    Button brnRegister;

    TextInputLayout firstNameWrapper, lastNameWrapper, emailWrapper, passwordWrapper, confPasswordWrapper, contactNoWrapper;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);


        mAuth = FirebaseAuth.getInstance();

        ufirstName = findViewById(R.id.userFirstName);
        ulastName = findViewById(R.id.userLastName);
        uemail = findViewById(R.id.userEmailAddress);
        upassword = findViewById(R.id.userPassword);
        uconfpassword = findViewById(R.id.userConfPassword);
        ucontactNo = findViewById(R.id.userContactNumber);

        firstNameWrapper = findViewById(R.id.userFirstNameWrapper);
        lastNameWrapper = findViewById(R.id.lastNameWrapper);
        emailWrapper = findViewById(R.id.userEmailAddressWrapper);
        passwordWrapper = findViewById(R.id.passwordWrapper);
        confPasswordWrapper = findViewById(R.id.confPasswordWrapper);
        contactNoWrapper = findViewById(R.id.contactNoWrapper);


        brnRegister = findViewById(R.id.btnRegister);

        brnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                if(mAuth.getCurrentUser() != null){
//                    //Korisnikot e najaven i moze da se redirektira kon drugo activity
//
//                }
//
//
//                else{
                {

                    final String firstName = ufirstName.getText().toString().trim();
                    final String lastName = ulastName.getText().toString().trim();
                    final String email = uemail.getText().toString().trim();
                    String password = upassword.getText().toString().trim();
                    String confpassword = uconfpassword.getText().toString().trim();
                    final String contactNo = ucontactNo.getText().toString().trim();

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
                        return;
                    }

                    if(contactNo.isEmpty()){
                        contactNoWrapper.setError("Внеси телефон за контакт!");
                        contactNoWrapper.requestFocus();
                        return;
                    }

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //treba da se dodadat info vo firebase DB
                                User user = new User(firstName,lastName,email,contactNo);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegistarActivity.this, "Креирањето на корисник е успешно.", Toast.LENGTH_LONG).show();
                                            //
                                            Intent intent = new Intent(RegistarActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            //
                                        }
                                        else{
                                            Toast.makeText(RegistarActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                            else{
                                Toast.makeText(RegistarActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }




            }
        });
    }
}
