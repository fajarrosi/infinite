package com.example.fajarir.Konsol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.util.PatternsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fajarir.Konsol.data.DataManager;
import com.example.fajarir.Konsol.dosen.DosenActivity;
import com.example.fajarir.Konsol.mahasiswa.MahasiswaActivity;
import com.example.fajarir.Konsol.model.Constant;
import com.example.fajarir.Konsol.model.ServerRequest;
import com.example.fajarir.Konsol.model.ServerResponse;
import com.example.fajarir.Konsol.rest.ApiClient;
import com.example.fajarir.Konsol.rest.ApiInterface;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public TextView name, email, pass,tv_register;
    private ProgressDialog mProgressDialog;
    private DataManager dataManager;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private Integer type;
    private String token;
    private SharedPreferences pref;
    private Button bt_login;
    private Animation fadeIn;
    private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataManager = new DataManager(this);
        email = (TextView) findViewById(R.id.et_email);
        pass = (TextView) findViewById(R.id.et_password);
        bt_login = (Button)findViewById(R.id.bt_login);
        tv_register = (TextView)findViewById(R.id.tv_register);
        relativeLayout =(RelativeLayout) findViewById(R.id.layout_login);
        checkUserIsExist();
        bt_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        pref = getPreferences(0);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayout.startAnimation(fadeIn);
    }

    private void changeActivity(){
        if (dataManager.getType() == 1) {
            startActivity(new Intent(this, DosenActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MahasiswaActivity.class));
            finish();
        }
    }

    private void checkUserIsExist() {
        if(!dataManager.getEmail().isEmpty()) {
           changeActivity();
        }
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void dismissLoading() {
        mProgressDialog.dismiss();
    }

    public static void revertCustomChatConfig() {
        Qiscus.getChatConfig()
                .setNotificationBigIcon(R.drawable.notif_icon)
                .setNotificationSmallIcon(R.drawable.notif_icon)
                .setSendButtonIcon(R.drawable.ic_qiscus_send)
                .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_attach);
    }

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Please wait...");
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                final String emaildsn = email.getText().toString();
                final String passdsn = pass.getText().toString();
                if (!PatternsCompat.EMAIL_ADDRESS.matcher(emaildsn).matches()) {
                    email.setError("Please insert a valid email!");
                    email.requestFocus();
                } else if (passdsn.isEmpty()) {
                    pass.setError("Please insert your user key!");
                    pass.requestFocus();
                }  else{
                    ServerRequest request = new ServerRequest();
                    request.setEmail(emaildsn);
                    request.setPassword(passdsn);
                    Call<ServerResponse> response = apiInterface.login(request);
                    response.enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            if(response.isSuccessful()){
                                ServerResponse res = response.body();
                                dataManager.setName(res.getData().getUser().getName());
                                dataManager.setDesc(res.getData().getUser().getDescription());
                                type = res.getData().getUser().getUserType();
                                token = res.getData().getUser().getAuthenticationToken();
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putBoolean(Constant.TAG,true);
                                editor.putBoolean(Constant.IS_LOGGED_IN,true);
                                editor.putString(Constant.EMAIL,res.getData().getUser().getEmail());
                                editor.putString(Constant.NAME,res.getData().getUser().getName());
                                editor.putString(Constant.DESCRIPTION,res.getData().getUser().getDescription());
                                editor.apply();
                                dataManager.setToken(token);
                                dataManager.setType(type);
                                showLoading();
                                Qiscus.setUser(emaildsn,passdsn)
                                        .withUsername(res.getData().getUser().getName())
                                        .save(new Qiscus.SetUserListener() {
                                            @Override
                                            public void onSuccess(QiscusAccount qiscusAccount) {
                                                dataManager.setEmail(emaildsn);
                                               changeActivity();
                                                dismissLoading();
                                            }

                                            @Override
                                            public void onError(Throwable throwable) {
                                                if (throwable instanceof HttpException) { //Error response from server
                                                    HttpException e = (HttpException) throwable;
                                                    try {
                                                        String errorMessage = e.response().errorBody().string();
                                                        Log.e(String.valueOf(this), errorMessage);
                                                        showError(errorMessage);
                                                    } catch (IOException e1) {
                                                        e1.printStackTrace();
                                                    }
                                                } else if (throwable instanceof IOException) { //Error from network
                                                    showError("Can not connect to qiscus server!");
                                                } else { //Unknown error
                                                    showError("Unexpected error!");
                                                }
                                                dismissLoading();
                                            }
                                        });
                            }else{
                                Toast.makeText(MainActivity.this, "Make sure you are registered", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {

                        }
                    });
                }break;
            case R.id.tv_register:
                startActivity(new Intent(MainActivity.this,Register.class));
                finish();
                break;
        }
    }


}
