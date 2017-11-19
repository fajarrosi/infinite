package com.example.fajarir.Konsol.mahasiswa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fajarir.Konsol.About;
import com.example.fajarir.Konsol.MainActivity;
import com.example.fajarir.Konsol.R;
import com.example.fajarir.Konsol.Tab.SlidingTabLayout;
import com.example.fajarir.Konsol.UserProfile;
import com.example.fajarir.Konsol.chat.ChatFragment;
import com.example.fajarir.Konsol.contact.ContactFragment;
import com.example.fajarir.Konsol.data.DataManager;
import com.example.fajarir.Konsol.reminder.AddAlarm;
import com.example.fajarir.Konsol.reminder.ListTodoFragment;
import com.qiscus.sdk.Qiscus;

/**
 * Created by WIN 8 on 15/08/2017.
 */

public class MahasiswaActivity extends AppCompatActivity {
    Toolbar toolbar;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private ChatFragment chatFragment;
    private DataManager dataManager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MahasiswaActivity.this, AddAlarm.class));
            }
        });
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Consol");
        toolbar.setBackgroundColor(getResources().getColor(R.color.blueSoft));
        setSupportActionBar(toolbar);

        mViewPager=(ViewPager)findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new MahasiswaPagerAdapter(getSupportFragmentManager(),this));

        mSlidingTabLayout=(SlidingTabLayout)findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.blueSoft));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.yellow));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_tab);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        fab.hide();
                        toolbar.setTitle("Contacts");
                        break;
                    case 1:
                        fab.hide();
                        toolbar.setTitle("Chats");
                        chatFragment.loadchat();
                        break;
                    case 2:
                        fab.show();
                        toolbar.setTitle("Reminder");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        dataManager = new DataManager(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabs,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            startActivity(new Intent(this, UserProfile.class));
            return true;
        }else if(id == R.id.logout){
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure wants to logout?")
                    .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Qiscus.clearUser();
                            dataManager.clear();
                            startActivity(new Intent(MahasiswaActivity.this, MainActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
    public class MahasiswaPagerAdapter extends FragmentPagerAdapter {
        private Context mContext;
        private String[] title = {"A","B","C"};
        int[] icons = new int[] {R.drawable.ic_account_circle_white_48dp, R.drawable.ic_chat_white_48dp,R.drawable.ic_alarm_white_48dp} ;
        private int heightIcon;

        public MahasiswaPagerAdapter(FragmentManager fm, Context c) {
            super(fm);
            mContext = c;
            double scale=c.getResources().getDisplayMetrics().density;
            heightIcon = (int) (24*scale+0.5f);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;
            if(position == 0){
                frag = new ContactFragment();
            }else if(position ==1){
                chatFragment = new ChatFragment();
                chatFragment.loadchat();
                //notifyDataSetChanged();
                return chatFragment;
            }else if(position ==2) {
                frag = new ListTodoFragment();
            }

            Bundle b = new Bundle();
            b.putInt("position",position);

            frag.setArguments(b);
            return frag;

        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable d = mContext.getResources().getDrawable(icons[position]);
            d.setBounds(0,0,heightIcon,heightIcon);

            ImageSpan is =new ImageSpan(d);

            SpannableString sp = new SpannableString(" ");
            sp.setSpan(is,0,sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return(sp);
        }
    }
}
