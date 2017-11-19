package com.example.fajarir.Konsol.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import static java.lang.Character.getType;

/**
 * Created by fajarir on 8/6/2017.
 */

public class DataManager {
    SharedPreferences sharedpreferences;
    Gson gson = new Gson();

    public DataManager(Context context) {
        sharedpreferences = context.getSharedPreferences("contact5", context.MODE_PRIVATE);
    }

    public void clear(){
        sharedpreferences.edit().clear().apply();
    }

    public void setToken(String token){
        sharedpreferences.edit().putString("token",token).apply();
    }
    public String getToken(){
       return sharedpreferences.getString("token","");
    }

    public void setType(Integer type){
        sharedpreferences.edit().putInt("type",type).apply();
    }
    public Integer getType(){
        return  sharedpreferences.getInt("type",0);
    }

    public void setDay(Integer day){
        sharedpreferences.edit().putInt("day",day).apply();
    }
    public Integer getDay(){
        return sharedpreferences.getInt("day",0);
    }
    public void setMonth(Integer month){
        sharedpreferences.edit().putInt("month",month).apply();
    }
    public Integer getMonth(){
        return sharedpreferences.getInt("month",0);
    }
    public void setYear(Integer year){
        sharedpreferences.edit().putInt("year",year).apply();
    }
    public Integer getYear(){
        return sharedpreferences.getInt("year",0);
    }

    public void setEmail(String email){
        sharedpreferences.edit().putString("id",email).apply();
    }

    public String getEmail(){
        return sharedpreferences.getString("id","");
    }
    public void setName(String name){sharedpreferences.edit().putString("name", name).apply();}
    public String getName(){return sharedpreferences.getString("name","");}
    public void setDesc(String desc){sharedpreferences.edit().putString("desc", desc).apply();}
    public String getDesc(){return sharedpreferences.getString("desc","");}

    public void setHour(Integer hour){
        sharedpreferences.edit().putInt("hour",hour).apply();
    }
    public Integer getHour(){
        return sharedpreferences.getInt("hour",0);
    }

    public void setMinutes(Integer minutes){
        sharedpreferences.edit().putInt("minutes",minutes).apply();
    }
    public Integer getMinutes(){
        return sharedpreferences.getInt("minutes",0);
    }


    public void setTitles(String titles){
        sharedpreferences.edit().putString("titles",titles).apply();
    }

    public String getTitles(){
        return sharedpreferences.getString("titles","");
    }

    public void setNotes(String notes) {
        sharedpreferences.edit().putString("notes",notes).apply();
    }
    public String getNotes() {
        return sharedpreferences.getString("notes","");
    }
}
