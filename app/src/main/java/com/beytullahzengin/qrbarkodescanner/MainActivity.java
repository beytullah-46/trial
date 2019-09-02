package com.beytullahzengin.qrbarkodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.beytullahzengin.qrbarkodescanner.fragments.FourFragment;
import com.beytullahzengin.qrbarkodescanner.fragments.ThreeFragment;
import com.beytullahzengin.qrbarkodescanner.fragments.TwoFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity  {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(R.string.Scanner);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_scan_code, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);


        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.Create);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_create, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(R.string.History);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_history_1, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText(R.string.Settings);
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_settings, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

    }

    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */

    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BarcodeReaderFragment(), "Scan");
        adapter.addFrag(new TwoFragment(), "Create");
        adapter.addFrag(new ThreeFragment(), "History");
        adapter.addFrag(new FourFragment(), "Setting");

        viewPager.setAdapter(adapter);

    }
    public Fragment getFragment(int pos) {
        return adapter.getItem(pos);
    }










}
