package com.beytullahzengin.qrbarkodescanner;

import android.content.Context;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;

import com.beytullahzengin.qrbarkodescanner.fragments.ThreeFragment;

import java.util.ArrayList;

/**
 * Created by SONU on 22/03/16.
 */
public class Toolbar_ActionMode_Callback implements ActionMode.Callback {

    private Context context;

    private ListView_Adapter listView_adapter;
    private ArrayList<Item_Model> message_models;
    private boolean isThreeFragment;


    public Toolbar_ActionMode_Callback(Context context, ListView_Adapter listView_adapter, ArrayList<Item_Model> message_models, boolean isListViewFragment) {
        this.context = context;

        this.listView_adapter = listView_adapter;
        this.message_models = message_models;
        this.isThreeFragment = isListViewFragment;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_main3, menu);//Inflate the menu over action mode
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                if (isThreeFragment) {
                    Fragment listFragment = new MainActivity().getFragment(2);//Get list view Fragment
                    if (listFragment != null)
                        //If list fragment is not null
                        ((ThreeFragment) listFragment).deleteRows();//delete selected rows
                }

                break;

        }
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {

        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode
        if (isThreeFragment) {
            listView_adapter.removeSelection();  // remove selection
            Fragment listFragment = new MainActivity().getFragment(2);
            if (listFragment != null)
                ((ThreeFragment) listFragment).setNullToActionMode();//Set action mode null



    }
}
}