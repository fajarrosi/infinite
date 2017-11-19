package com.example.fajarir.Konsol.contact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.fajarir.Konsol.R;
import com.example.fajarir.Konsol.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fajarir on 7/29/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private ArrayList<User> user;
    private OnClickListener onClickListener;

    public ContactAdapter(ArrayList<User> user) {
        this.user = user;
    }

    public ArrayList<User> getUser() {
        return user;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemName,status;
        public OnClickListener onClickListener;
        private ImageView imageView;

        public ViewHolder(View itemView, OnClickListener onClickListener) {
            super(itemView);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
            itemName = (TextView) itemView.findViewById(R.id.tv_name);
            status = (TextView)itemView.findViewById(R.id.tv_status);
            imageView = (ImageView) itemView.findViewById(R.id.iv_photo);
        }


        @Override
        public void onClick(View view) {
            if (onClickListener != null){
                onClickListener.onClick(getAdapterPosition());
            }
        }
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(v,onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder viewHolder, int i) {
// Set item views based on your views and data model
        viewHolder.itemName.setText(user.get(i).getName());
        String firstLetter = firstWord(String.valueOf(user.get(i).getName()).toUpperCase());
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter,color);
        viewHolder.imageView.setImageDrawable(drawable);
        viewHolder.itemName.setText(user.get(i).getName());
        if(user.get(i).getUserType() == 1) viewHolder.status.setText("Consultant");
        else viewHolder.status.setText("Student");
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
        return user.size();
    }

    public interface OnClickListener{
        void onClick(int adapterPosition);
    }

}
