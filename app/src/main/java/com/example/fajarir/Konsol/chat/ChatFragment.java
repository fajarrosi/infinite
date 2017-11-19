package com.example.fajarir.Konsol.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fajarir.Konsol.Custom_chat;
import com.example.fajarir.Konsol.R;
import com.example.fajarir.Konsol.R2;
import com.example.fajarir.Konsol.model.RoomsInfo;
import com.example.fajarir.Konsol.model.ServerChatResponse;
import com.example.fajarir.Konsol.rest.ApiInterface;
import com.example.fajarir.Konsol.rest.QClient;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusChatRoom;
import com.qiscus.sdk.data.remote.QiscusApi;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
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

public class ChatFragment extends Fragment {
    @BindView(R2.id.rv_chats)
    RecyclerView rvChats;
    private ChatAdapter adapter;
    private Unbinder unbinder;
    ApiInterface apiInterface = QClient.getClient().create(ApiInterface.class);
    ArrayList<RoomsInfo> chat;

    private ProgressDialog mProgressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadchat();
    }


    public void loadchat() {
        Call<ServerChatResponse> response = apiInterface.getListChat(Qiscus.getToken());
        response.enqueue(new Callback<ServerChatResponse>() {
            @Override
            public void onResponse(Call<ServerChatResponse> call, Response<ServerChatResponse> response) {
                if (response.isSuccessful()) {
                    final ServerChatResponse server = response.body();
                    chat = new ArrayList<>(Arrays.asList(server.getResults().getRoomsInfo()));
                    adapter = new ChatAdapter(chat);
                   adapter.setChat(chat);
                    adapter.notifyDataSetChanged();
                    adapter.setOnClickListener(new ChatAdapter.OnClickListener() {
                        @Override
                        public void onClick(RoomsInfo roomsInfo) {
                            OpenChatWith(roomsInfo.getRoomId(),roomsInfo.getRoomName());
                        }
                    });

                    rvChats.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), Qiscus.getToken(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerChatResponse> call, Throwable t) {
            }
        });
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvChats.setLayoutManager(layoutManager);
    }

    private void OpenChatWith(Integer roomId,final String roomName) {
        showLoading();
        QiscusApi.getInstance()
                .getChatRoom(roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<QiscusChatRoom, Intent>() {
                    @Override
                    public Intent call(QiscusChatRoom qiscusChatRoom) {
                        Log.d("test", "call: " + qiscusChatRoom.toString());
                        return Custom_chat.generateIntent(getContext(), qiscusChatRoom,roomName);
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

    private void revertCustomChatConfig() {
        Qiscus.getChatConfig()
                .setAppBarColor(R.color.blueSoft)
                .setSendButtonIcon(R.drawable.ic_qiscus_send)
                .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_attach);
    }



}
