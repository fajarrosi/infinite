package com.example.fajarir.Konsol.reminder;

/**
 * Created by WIN 8 on 15/08/2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fajarir.Konsol.MainActivity;
import com.example.fajarir.Konsol.R;
import com.example.fajarir.Konsol.mahasiswa.MahasiswaActivity;
import com.qiscus.sdk.Qiscus;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static android.app.Activity.RESULT_OK;

public class ListTodoFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    private ListView listView;
    private Context mContext;
    private SimpleCursorAdapter adapter;
    private DBManager dbManager;
    final String[] from = new String[] { DatabaseHelper._ID,DatabaseHelper.TITLE, DatabaseHelper.DESC,DatabaseHelper.TIME };

    final int[] to = new int[] { R.id.num_id,R.id.aktifitas, R.id.desc,R.id.datetime };
    private Cursor cursor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DBManager(getContext());
        dbManager.open();
        cursor = dbManager.fetch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        View rootView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        rootView.setTag(TAG);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setEmptyView(rootView.findViewById(R.id.empty));
        adapter = new SimpleCursorAdapter(getContext(), R.layout.custom_todolist_row, cursor, from, to, 0);

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView idTextView = (TextView) view.findViewById(R.id.num_id);
                final String id = idTextView.getText().toString();
                final long _id;
                _id = Long.parseLong(id);
                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setTitle("DELETE")
                        .setMessage("Are you sure wants to Delete?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbManager.delete(_id);
//                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
