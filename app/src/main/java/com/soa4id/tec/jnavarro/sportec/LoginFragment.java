package com.soa4id.tec.jnavarro.sportec;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import credentials.CredentialsHelper;

public class LoginFragment extends Fragment {
    private View mView;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonSummit;
    private CredentialsHelper mCredentialsHelper;


    /**
     *
     */
    public LoginFragment() {
    }

    /**
     * Creates the view using the login_fragment layout as parameter to inflate this fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mCredentialsHelper = new CredentialsHelper();

        this.mView = inflater.inflate(R.layout.login_fragment,container,false);

        this.mEditTextEmail = mView.findViewById(R.id.login_fragment_email);
        this.mEditTextPassword = mView.findViewById(R.id.login_fragment_password);
        this.mButtonSummit = mView.findViewById(R.id.login_fragment_summit);

        this.mButtonSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        return mView;
    }

    /**
     * This function is called when the user wants to login.
     */
    public void login(){
        String email = this.mEditTextEmail.getText().toString();

        String password = this.mEditTextPassword.getText().toString();
        if ( email.isEmpty() || password.isEmpty()){
            // snack bar
            String warning =
                    mView.getResources().getString(R.string.credential_activity_successful_login_warning);
            Snackbar.make(
                    this.mView,
                    warning,
                    Snackbar.LENGTH_SHORT).show();

        }else{
            String greeting =
                    mView.getResources().getString(R.string.credential_activity_successful_login_greeting);
            Snackbar.make(
                    this.mView,
                    String.format(greeting,email),
                    Snackbar.LENGTH_SHORT).show();

            //Intent intent = new Intent(mView.getContext(), MainActivity.class);
            //startActivity(intent);
        }
    }
}
