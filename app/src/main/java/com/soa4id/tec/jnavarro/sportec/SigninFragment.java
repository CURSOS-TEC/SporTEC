
package com.soa4id.tec.jnavarro.sportec;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SigninFragment extends Fragment {
    private View mView;
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private EditText mEditTextPasswordConfirm;
    private Button   mButtonSummit;
    private Button   mButtonTakePhoto;

    /**
     *
     */
    public SigninFragment() {

    }

    /**
     * Creates the view using the signin_fragment layout as parameter to inflate this fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.signin_fragment,container,false);
        return this.mView;
    }
}
