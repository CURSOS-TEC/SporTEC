package com.soa4id.tec.jnavarro.sportec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import credentials.CredentialsHelper;

public class LoginActivity extends AppCompatActivity {
    private View mView;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonSummit;
    private Button mOpenSignIn;
    private CredentialsHelper mCredentialsHelper;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.mCredentialsHelper = new CredentialsHelper();

        this.mEditTextEmail =findViewById(R.id.login_fragment_email);
        this.mEditTextPassword = findViewById(R.id.login_fragment_password);
        this.mButtonSummit = findViewById(R.id.login_fragment_summit);
        this.mOpenSignIn = findViewById(R.id.login_signin_fragment_summit);


        this.mButtonSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
        this.mOpenSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });



    }
    /**
     * This function is called when the user wants to login.
     */
    public void login(View v){
        String email = this.mEditTextEmail.getText().toString();

        String password = this.mEditTextPassword.getText().toString();
        if ( email.isEmpty() || password.isEmpty()){
            // snack bar
            String warning =
                    getResources().getString(R.string.credential_activity_successful_login_warning);
            Snackbar.make(
                    v,
                    warning,
                    Snackbar.LENGTH_SHORT).show();

        }else{
            String greeting =
                    getResources().getString(R.string.credential_activity_successful_login_greeting);

            this.mCredentialsHelper.login(LoginActivity.this,email,password);

        }
        Log.i("JSON", "LoginFragment post login");//TODO: Delete this on production
    }
}
