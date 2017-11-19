package com.example.fajarir.Konsol;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.PatternsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fajarir.Konsol.model.RegisterRequest;
import com.example.fajarir.Konsol.model.Registeruser;
import com.example.fajarir.Konsol.model.ServerResponse;
import com.example.fajarir.Konsol.rest.ApiClient;
import com.example.fajarir.Konsol.rest.ApiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WIN 8 on 17/08/2017.
 */

public class Register extends AppCompatActivity implements View.OnClickListener {
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private AppCompatButton btn_register;
    private EditText et_email,et_password,et_name,et_prodi;
    private TextView tv_login;
    private ProgressBar progress;
    private Spinner et_status;
    private ProgressDialog mProgressDialog;
    private Animation fadeIn;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        btn_register = (AppCompatButton)findViewById(R.id.btn_register);
        tv_login = (TextView)findViewById(R.id.tv_login);
        et_name = (EditText)findViewById(R.id.et_name);
        et_email = (EditText)findViewById(R.id.et_email_reg);
        et_password = (EditText)findViewById(R.id.et_password_reg);
        et_prodi = (EditText)findViewById(R.id.et_prodi);
        et_status = (Spinner) findViewById(R.id.et_status);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout_register);
        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayout.startAnimation(fadeIn);
        String []listspinner = new String[]{
                "Student","Consultant"
        };
        final List<String>spinnerlist = new ArrayList<>(Arrays.asList(listspinner));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,spinnerlist);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        et_status.setAdapter(spinnerArrayAdapter);
    }

    public void Success(){
        new AlertDialog.Builder(this)
                .setTitle("Register")
                .setMessage("Registration successful.Please Login to access your account")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Register.this, MainActivity.class));
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_login:
                startActivity(new Intent(Register.this,MainActivity.class));
                finish();
                break;

            case R.id.btn_register:
                final String name = et_name.getText().toString();
                final String email = et_email.getText().toString();
                final String password = et_password.getText().toString();
                final String prodi = et_prodi.getText().toString();
                int typeUser = et_status.getSelectedItemPosition();
                if(name.isEmpty()){
                    et_name.setError("Please insert your name");
                    et_name.requestFocus();
                }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
                    et_email.setError("Please insert a valid email!");
                    et_email.requestFocus();
                } else if (prodi.isEmpty()) {
                    et_prodi.setError("Please insert your majors!");
                    et_prodi.requestFocus();
                } else if (password.isEmpty()) {
                    et_password.setError("Please insert your password!");
                    et_password.requestFocus();
                }else{
                    final RegisterRequest request = new RegisterRequest();
                    request.setEmail(email);
                    request.setPassword(password);
                    request.setName(name);
                    request.setDescription(prodi);
                    request.setUser_type(typeUser);
                    final Registeruser registeruser = new Registeruser();
                    registeruser.setUser(request);
                    Call<ServerResponse> response =apiInterface.register(registeruser);
                    response.enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            if(response.isSuccessful()){
                                Success();
                            }else{
                                Toast.makeText(Register.this, "gagal", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {

                        }
                    });


                }
                break;

        }
    }

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Please wait...");
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void dismissLoading() {
        mProgressDialog.dismiss();
    }
}
