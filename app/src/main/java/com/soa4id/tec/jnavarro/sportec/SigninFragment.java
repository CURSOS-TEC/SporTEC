
package com.soa4id.tec.jnavarro.sportec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
    private Button   mButtonSports;

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

        this.mEditTextName = this.mView.findViewById(R.id.signin_fragment_name);
        this.mEditTextEmail = this.mView.findViewById(R.id.signin_fragment_email);
        this.mEditTextPassword = this.mView.findViewById(R.id.login_fragment_password);
        this.mEditTextPasswordConfirm = this.mView.findViewById(R.id.signin_fragment_password_confirm);
        this.mButtonSummit = this.mView.findViewById(R.id.signin_fragment_summit);
        this.mButtonTakePhoto = this.mView.findViewById(R.id.signin_fragment_take_photo);
        this.mButtonSports = this.mView.findViewById(R.id.signin_fragment_select_sports);

        this.mButtonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);

            }
        });

        this.mButtonSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.mButtonSports.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        return this.mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        Snackbar.make(
                this.mView,
                String.valueOf(bitmap.getGenerationId()),
                Snackbar.LENGTH_SHORT).show();
    }
}
