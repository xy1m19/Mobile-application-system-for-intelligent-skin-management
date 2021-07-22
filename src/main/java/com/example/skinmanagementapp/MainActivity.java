package com.example.skinmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.example.skinmanagementapp.Admin.AdminSettingInformation;
import com.example.skinmanagementapp.Admin.Admin_Register;
import com.example.skinmanagementapp.User.UserHomeActivity;
import com.example.skinmanagementapp.User.UserRegister;
import com.example.skinmanagementapp.User.UserSettingInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    ImageView imageView_login;
    Button button;
    Button button2;
    Button button4;
    Spinner spinner;
    TextView Email, Password;
    TextInputLayout loginEmailTextInputLayout, loginPasswordTextInputLayout;
    TextInputEditText loginEmailTextInput, loginPasswordTextInput;

    FirebaseDatabase mData;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mAuth=FirebaseAuth.getInstance();
        mData= FirebaseDatabase.getInstance();
        // UserID = user.getUid();

        imageView_login = findViewById(R.id.imageView_login);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button4);
        spinner = findViewById(R.id.spinner);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        loginEmailTextInputLayout = findViewById(R.id.loginEmailTextInputLayout);
        loginPasswordTextInputLayout = findViewById(R.id.loginPasswordTextInputLayout);
        loginEmailTextInput = findViewById(R.id.loginEmailTextInput);
        loginPasswordTextInput = findViewById(R.id.loginPasswordTextInput);


        Forgotten();
        check();

    }
    private void Forgotten() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });
    }
    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout = new LinearLayout(this);
        final EditText emailEt = new EditText(this);
        emailEt.setHint("Email");

        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEt.setMinEms(16);
        linearLayout.addView(emailEt);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = emailEt.getText().toString().trim();
                beginRecovery(email);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();;
    }
    private void beginRecovery(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Email sent",Toast.LENGTH_LONG).show();
                        }
                        else {

                            Toast.makeText(MainActivity.this,"Failed....",Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show
                        ();
            }
        });
    }
    private static final String EMAIL_PATTERN = "\"^[a-zA-Z0-9][\\\\w\\\\.-]*[a-zAZ0-9]@[a-zA-Z0-9][\\\\w\\\\.-]*[a-zA-Z0-9]\\\\.[a-zA-Z][a-zA-Z\\\\.]*[a-zA-Z]$\"";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    public void login(View v) {
        String username =
                Objects.requireNonNull(loginEmailTextInputLayout.getEditText()).getText().toString();
        if (!validateEmail(username)) {
            loginEmailTextInputLayout.setError("error：format");
        } else {
            loginEmailTextInputLayout.setError("");
        }
    }
    public boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void check() {
        loginPasswordTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1,
                                          int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int
                    i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if
                (Objects.requireNonNull(loginPasswordTextInput.getText()).toString().length() > 10 ||
                        loginPasswordTextInput.getText().toString().length() < 6) {
                    loginPasswordTextInputLayout.setError("Length of password 6-10");
                } else {
                    loginPasswordTextInputLayout.setError("");
                }
            }
        });






        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (spinner.getSelectedItemPosition()) {
                    case 0:
                        intent.setClass(MainActivity.this, UserRegister.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this, Admin_Register.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEvent();
            }
        });
    }

    private void loginEvent() {
        //ProgressBar.setVisibility(View.VISIBLE)

        mAuth.signInWithEmailAndPassword(Objects.requireNonNull(loginEmailTextInput.getText()).toString(),
                Objects.requireNonNull(loginPasswordTextInput.getText()).toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent1 = new Intent();
                            Intent intent2 = new Intent();
                            switch (spinner.getSelectedItemPosition()) {
                                case 0:
                                    intent1.setClass(MainActivity.this, UserHomeActivity.class);
                                    startActivity(intent1);
                                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    intent2.setClass(MainActivity.this, AdminSettingInformation.class);
                                    startActivity(intent2);
                                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this,"Sign failed! Please Email or Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }







    }
