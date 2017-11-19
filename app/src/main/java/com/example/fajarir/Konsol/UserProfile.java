package com.example.fajarir.Konsol;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fajarir.Konsol.data.DataManager;
import com.example.fajarir.Konsol.model.Constant;
import com.example.fajarir.Konsol.model.ServerResponse;
import com.example.fajarir.Konsol.model.UpdateProfile;
import com.example.fajarir.Konsol.model.UpdateProfileRequest;
import com.example.fajarir.Konsol.rest.ApiInterface;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_descProfile, tv_name_prof,tv_statusProf;
    private EditText et_nameProf;
    private SharedPreferences pref;
    private Button btn_save;
    private ProgressBar progress;
    private LinearLayout linearLayout;
    private DataManager dataManager;
    private Toolbar toolbar;
    private Animation slideUp;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        dataManager = new DataManager(this);
        tv_descProfile = (TextView) findViewById(R.id.tv_descProfile);
        tv_name_prof = (TextView) findViewById(R.id.tv_name_prof);
        et_nameProf = (EditText) findViewById(R.id.et_name_prof);
        tv_statusProf = (TextView) findViewById(R.id.tv_status_prof);
        linearLayout =(LinearLayout) findViewById(R.id.edit_profile_layout);
        slideUp = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slideup_anim);
        progress=(ProgressBar) findViewById(R.id.progress_prof);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("User Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getPreferences(0);
        tv_descProfile.setText("You login using : "+ dataManager.getEmail());
        tv_name_prof.setText(dataManager.getName());
        et_nameProf.setText(dataManager.getName());
        tv_statusProf.setText(dataManager.getDesc());
        linearLayout.startAnimation(slideUp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                String name = et_nameProf.getText().toString();
                final String token = "Token token="+dataManager.getToken();
                progress.setVisibility(View.VISIBLE);
                if (!name.isEmpty()) {
//                    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//                    httpClient.addInterceptor(new Interceptor() {
//                        @Override
//                        public okhttp3.Response intercept(Chain chain) throws IOException {
//                            Request original = chain.request();
//
//                            Request request = original.newBuilder()
//                                    .header("Authorization",token)
//                                    .method(original.method(), original.body())
//                                    .build();
//                            return chain.proceed(request);
//                        }
//                    });
//                    OkHttpClient client = httpClient.build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constant.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            //.client(client)
                            .build();

                    ApiInterface requestInterface = retrofit.create(ApiInterface.class);
                    UpdateProfile request = new UpdateProfile();
                    request.setName(name);

                    UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
                    updateProfileRequest.setUser(request);

                    Call<ServerResponse> response = requestInterface.updateProfile(token, updateProfileRequest);
                    response.enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                            if (response.isSuccessful()) {
                                ServerResponse resp = response.body();
                                if (resp.getStatus().equals(Constant.STATUS)) {
                                    progress.setVisibility(View.INVISIBLE);
                                    Success(true);
                                    tv_name_prof.setText(resp.getData().getUser().getName());
                                    dataManager.setName(resp.getData().getUser().getName());
                                    //Snackbar.make(getWindow().getDecorView().getRootView(), "Your name was successfully replaced to" + resp.getData().getUser().getName(), Snackbar.LENGTH_LONG).show();
                                } else {
                                    progress.setVisibility(View.INVISIBLE);
                                    Success(false);
                                    //Snackbar.make(getWindow().getDecorView().getRootView(), "Update Failed", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Log.i("debug", "onResponse: Update Failed");
                                progress.setVisibility(View.INVISIBLE);
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Response Failed", Snackbar.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ServerResponse> call, Throwable t) {
                            progress.setVisibility(View.INVISIBLE);
                            Log.d(Constant.TAG, "failed");
                            Snackbar.make(getWindow().getDecorView().getRootView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
                break;
        }
    }
    public void Success(boolean x){
        String result = "";
        if (x==true){
            result = "Your name has changed";
        }else {
            result = "Your name failed to update";
        }
        new AlertDialog.Builder(this)
                .setTitle("Notification")
                .setMessage(result)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        startActivity(new Intent(UserProfile.this, UserProfile.class));
//                        finish();
                    }
                })
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
