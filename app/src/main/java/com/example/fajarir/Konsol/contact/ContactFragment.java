package com.example.fajarir.Konsol.contact;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fajarir.Konsol.Custom_chat;
import com.example.fajarir.Konsol.R;
import com.example.fajarir.Konsol.R2;
import com.example.fajarir.Konsol.chat.ChatAdapter;
import com.example.fajarir.Konsol.data.DataManager;
import com.example.fajarir.Konsol.model.ServerContactResponse;
import com.example.fajarir.Konsol.model.User;
import com.example.fajarir.Konsol.rest.ApiClient;
import com.example.fajarir.Konsol.rest.ApiInterface;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by fajarir on 7/28/2017.
 */

public class ContactFragment extends Fragment {
    @BindView(R2.id.rv_contacts)RecyclerView rvContacts;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private ContactAdapter adapter;
    private ChatAdapter adapter2;
    private Unbinder unbinder;
    private ProgressDialog mProgressDialog;
    private DataManager dataManager;
    private ArrayList<User> user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager = new DataManager(getContext());
        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
    }

    private void loadUser() {
        String token  = "Token token="+ dataManager.getToken();
        Call<ServerContactResponse> response = apiInterface.getDataUser(token);
        response.enqueue(new Callback<ServerContactResponse>() {
            @Override
            public void onResponse(Call<ServerContactResponse> call, Response<ServerContactResponse> response) {
                if(response.isSuccessful()) {
                    ServerContactResponse serverContactResponse = response.body();
                    user = new ArrayList<>(Arrays.asList(serverContactResponse.getData().getUser()));
                    adapter = new ContactAdapter(user);
                    rvContacts.setAdapter(adapter);
                    adapter.setOnClickListener(new ContactAdapter.OnClickListener() {
                        @Override
                        public void onClick(int adapterPosition) {
                            OpenChatWith(adapter.getUser().get(adapterPosition));
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "Gagal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerContactResponse> call, Throwable t) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvContacts.setLayoutManager(layoutManager);
//        rvContacts.setAdapter(adapter);
        rvContacts.setItemAnimator(new SlideInUpAnimator());

    }

    private void OpenChatWith(final User user) {

        showLoading();
        Qiscus.buildChatRoomWith(user.getEmail())
                .withTitle(user.getName())
                .build()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<QiscusChatRoom, Intent>() {
                    @Override
                    public Intent call(QiscusChatRoom qiscusChatRoom) {

                        return Custom_chat.generateIntent(getContext(), qiscusChatRoom, user.getName());
                    }
                })
                .subscribe(new Action1<Intent>() {
                    @Override
                    public void call(Intent intent) {
                        startActivity(intent);
                        dismissLoading();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        showError(throwable.getMessage());
                        dismissLoading();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        loadUser();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Please wait...");
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
    private void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
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

}
