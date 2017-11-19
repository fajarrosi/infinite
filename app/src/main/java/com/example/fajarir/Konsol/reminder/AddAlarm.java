package com.example.fajarir.Konsol.reminder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fajarir.Konsol.R;
import com.example.fajarir.Konsol.data.DataManager;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WIN 8 on 22/08/2017.
 */

public class AddAlarm extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    CircleImageView iv_close;
    Boolean cek = false;
    private DataManager dataManager;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static final String TAG = AddAlarm.class.getSimpleName();
    private TextView et_title, et_notes, tv_time;
    private TextView tv_date;
    private TextView tv_none;
    private DBManager dbManager;
    private TextView txtToneSelection;


    public void date() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dataManager.setDay(dayOfMonth);
                        int month = monthOfYear + 1;
                        dataManager.setMonth(monthOfYear);
                        dataManager.setYear(year);
                        tv_date.setText(dayOfMonth + "-" + (month) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void time() {
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String AM_PM;
                        mHour = hourOfDay;
                        mMinute = minute;
                        if (hourOfDay < 12) {
                            AM_PM = "AM";

                        } else {
                            AM_PM = "PM";
                            mHour = mHour - 12;

                        }
                        tv_time.setText(mHour + ":" + mMinute + " " + AM_PM);
                        dataManager.setHour(hourOfDay);
                        dataManager.setMinutes(minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addalarm);
        initview();
    }

    public void initview() {
        dataManager = new DataManager(this);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.blueSoft));
        et_title = (TextView) findViewById(R.id.et_title);
        et_notes = (TextView) findViewById(R.id.et_note);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_none = (TextView) findViewById(R.id.tv_none);
        iv_close = (CircleImageView) findViewById(R.id.iv_close);
        toolbar.setTitle("Add Task");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        tv_date.setText(mDay + "-" + (mMonth + 1) + "-" + mDay);
        // Get Current Time
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        String AM_PM;
        if (mHour < 12) {
            AM_PM = "AM";

        } else {
            AM_PM = "PM";
            mHour = mHour - 12;
        }
        tv_time.setText(mHour + ":" + mMinute + " " + AM_PM);
        dbManager = new DBManager(this);
        dbManager.open();

    }

    public void onClose(View view) {
        cek = false;
        tv_time.setVisibility(View.GONE);
        iv_close.setVisibility(View.GONE);
        tv_date.setVisibility(View.GONE);
        tv_none.setVisibility(View.VISIBLE);
    }

    public void onSet(View view) {
        tv_time.setVisibility(View.VISIBLE);
        iv_close.setVisibility(View.VISIBLE);
        tv_date.setVisibility(View.VISIBLE);
        tv_none.setVisibility(View.GONE);
        cek = true;
    }

    public void setDate(View view) {
        date();
        cek = true;
    }

    public void setTime(View view) {
        time();
        cek = true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you s
        // pecify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
            Toast.makeText(this, "No Task is created", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.done) {
            onDone();


        }
        return true;
    }

    private void onDone() {

        String title = et_title.getText().toString();
        String notes = et_notes.getText().toString();
        String date = tv_date.getText().toString();
        String time = tv_time.getText().toString();
        String datetime = date + " "+ time;
        dbManager.insert(title, notes,datetime);
        dataManager.setTitles(title);
        dataManager.setNotes(notes);
        if (cek) {

            finish();
          startAlarm();
        } else {
            finish();
            Toast.makeText(this, "no alarm", Toast.LENGTH_SHORT).show();
        }
    }

    private void startAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingintent;
            Intent intent;
            intent = new Intent(this, AlarmNotif.class);
            pendingintent = PendingIntent.getBroadcast(this, 0, intent, 0);
            Calendar calendar = getAlarmDate();
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingintent);
    }

    public Calendar getAlarmDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, dataManager.getYear());
        calendar.set(Calendar.MONTH, dataManager.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, dataManager.getDay());
        calendar.set(Calendar.HOUR_OF_DAY,dataManager.getHour());
        calendar.set(Calendar.MINUTE, dataManager.getMinutes());
        return calendar;
    }

}
