package com.beytullahzengin.qrbarkodescanner.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.beytullahzengin.qrbarkodescanner.R;
import com.beytullahzengin.qrbarkodescanner.ResultActivity;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class FourFragment extends Fragment {

    private ActionMode mActionMode;

    TextView textView;
    SearchView inputSearch;
    CheckBox checkBox;


    ListView lv;
    List<ListView> items;
    private View text1;

    public FourFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_for, container, false);

        lv = view.findViewById(R.id.l1);

        checkBox.setVisibility(View.INVISIBLE);


        Gson gson = new Gson();
        SharedPreferences sp =  getActivity().getSharedPreferences("list", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sp.getAll();
        int size = allEntries.entrySet().size();
        if(size == 0){
            final String[] display = getResources().getStringArray(R.array.history);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,display);
            lv.setAdapter(adapter);
        }else{
            int siez = size-1;
            final String[] display = new String [siez];
            for(int i=0;i<siez;i++){
                String temp =  sp.getString(String.valueOf(i),"");
                final Barcode b1 = gson.fromJson(temp,Barcode.class);
                String pr = b1.displayValue;
                display[i]= pr;
            }
            Collections.reverse(Arrays.asList(display));
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>( getActivity(),android.R.layout.simple_list_item_1,display);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    view.setSelected(true);
                    checkBox.setChecked(true);
                    int siez = display.length;
                    int x = siez-i-1;
                    action(x);

                }
            });


            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int i, long id) {

                    if (mActionMode != null){
                        return false;
                    }
                    ((AppCompatActivity) getActivity()).startSupportActionMode(mActionModeCallback);

                    return true;
                }
            });

        }
        intializeViews();

        return view;

    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_main3, menu);
            mode.setTitle("Choose your option");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:

                    delete();

                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    private void intializeViews() {


        lv.setTextFilterEnabled(true);

    }

    public void action(int i){
        Gson gson = new Gson();
        SharedPreferences sp = getActivity().getSharedPreferences("list", Context.MODE_PRIVATE);
        String temp =  sp.getString(String.valueOf(i),"");
        final Barcode b1 = gson.fromJson(temp,Barcode.class);
        Intent my = new Intent(getActivity(), ResultActivity.class);
        my.putExtra("type",b1);
        startActivity(my);
        getActivity().finish();

    }




    public void delete(){



    }*/


}
