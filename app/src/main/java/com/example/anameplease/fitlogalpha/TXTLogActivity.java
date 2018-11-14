package com.example.anameplease.fitlogalpha;

import android.os.Bundle;

import com.example.anameplease.fitlogalpha.databinding.ActivityTxtlogBinding;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class TXTLogActivity extends AppCompatActivity {

    private ActivityTxtlogBinding binding;

    private static final int NUM_PAGES = 2;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_txtlog);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        mPagerAdapter = new TXTLogActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
        binding.viewPager2.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_out);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return  new AppendNoteFragment();
            else
                return  new ViewTXTFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
