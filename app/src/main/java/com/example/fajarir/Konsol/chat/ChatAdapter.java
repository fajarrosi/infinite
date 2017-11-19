package com.example.fajarir.Konsol.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.fajarir.Konsol.DateSetting;
import com.example.fajarir.Konsol.R;
import com.example.fajarir.Konsol.model.RoomsInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fajarir on 8/6/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    ArrayList<RoomsInfo> chat;

    public ChatAdapter(ArrayList<RoomsInfo> chat) {
        this.chat = chat;
    }

    private OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ArrayList<RoomsInfo> getChat() {
        notifyDataSetChanged();
        return chat;
    }

    public void setChat(ArrayList<RoomsInfo> chat) {
        notifyDataSetChanged();
        this.chat = chat;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RoomsInfo roomsInfo;
        public OnClickListener onClickListener;
        public TextView itemName,itemMessage,time,unread;
        public FrameLayout layoutUnreadCount;
        public ImageView imageView;

        public ViewHolder(View itemView, OnClickListener onClickListener) {
            super(itemView);
            this.onClickListener = onClickListener;
            itemName= (TextView)itemView.findViewById(R.id.tv_chatroom_name);
            imageView= (ImageView) itemView.findViewById(R.id.iv_photo);
            itemMessage = (TextView)itemView.findViewById(R.id.tv_last_msg);
            time=(TextView)itemView.findViewById(R.id.tv_time);
            unread = (TextView)itemView.findViewById(R.id.tv_unread_count);
            layoutUnreadCount = (FrameLayout) itemView.findViewById(R.id.layout_unread_count);
            itemView.setOnClickListener(this);
        }
        public void setRoomsInfo(RoomsInfo roomsInfo){
            this.roomsInfo = roomsInfo;
        }
        @Override
        public void onClick(View view) {
            if (onClickListener != null){
                onClickListener.onClick(roomsInfo);
            }
        }
    }
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        ChatAdapter.ViewHolder viewHolder = new ChatAdapter.ViewHolder(v,onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        holder.itemName.setText(chat.get(position).getRoomName());
        String firstLetter = firstWord(String.valueOf(chat.get(position).getRoomName()).toUpperCase());
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter,color);
        holder.imageView.setImageDrawable(drawable);
        holder.itemMessage.setText(chat.get(position).getLastCommentMessage());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        try {
            Date tanggal = formatter.parse(chat.get(position).getLastCommentTimestamp());
            holder.time.setText(DateSetting.getLastMessageTimestamp(tanggal));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.unread.setText(String.format("%d", chat.get(position).getUnreadCount()));
        if (chat.get(position).getUnreadCount() == 0) holder.layoutUnreadCount.setVisibility(View.GONE);
        else holder.layoutUnreadCount.setVisibility(View.VISIBLE);
        holder.setRoomsInfo(chat.get(position));
    }
    public String firstWord(String name) {
        String first = "";
        int x = 0;
        name = name.replaceAll("[.,]","");
        for (String s:name.split(" ")){
            x++;
            first+=s.charAt(0);
            if (x==2){
                break;
            }
        }
        return first;
    }

    @Override
    public int getItemCount() {
        if(chat == null){
            return  0;
        }
        return chat.size();
    }

    public interface OnClickListener{
        void onClick(RoomsInfo roomsInfo);
    }

}
