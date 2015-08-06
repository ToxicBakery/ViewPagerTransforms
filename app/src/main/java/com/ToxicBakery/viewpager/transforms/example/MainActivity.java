/*
 * Copyright 2014 Toxic Bakery
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ToxicBakery.viewpager.transforms.example;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.TransformerType;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity
		implements OnNavigationListener {

    private static final String KEY_SELECTED_PAGE = "KEY_SELECTED_PAGE";
    private static final String KEY_SELECTED_CLASS = "KEY_SELECTED_CLASS";

    private int mSelectedItem;
    private ViewPager mPager;
	private ArrayList<TransformerType> transformers;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int selectedPage = 0;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(KEY_SELECTED_CLASS);
            selectedPage = savedInstanceState.getInt(KEY_SELECTED_PAGE);
        }

        final ArrayAdapter<TransformerType> actionBarAdapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, TransformerType.values());

        setContentView(R.layout.activity_main);

		PageAdapter mAdapter = new PageAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.container);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(selectedPage);

        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setListNavigationCallbacks(actionBarAdapter, this);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

            //noinspection ResourceType
            actionBar.setDisplayOptions(actionBar.getDisplayOptions() ^ ActionBar.DISPLAY_SHOW_TITLE);

            actionBar.setSelectedNavigationItem(mSelectedItem);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int position, long itemId) {
        mSelectedItem = position;
        mPager.setPageTransformer(true, TransformerType.values()[position].getTransformer());
        return true;
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_SELECTED_CLASS, mSelectedItem);
        outState.putInt(KEY_SELECTED_PAGE, mPager.getCurrentItem());
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String EXTRA_POSITION = "EXTRA_POSITION";
        private static final int[] COLORS = new int[] { 0xFF33B5E5, 0xFFAA66CC, 0xFF99CC00, 0xFFFFBB33, 0xFFFF4444 };

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final int position = getArguments().getInt(EXTRA_POSITION);
            final TextView textViewPosition = (TextView) inflater.inflate(R.layout.fragment_main, container, false);
            textViewPosition.setText(Integer.toString(position));
            textViewPosition.setBackgroundColor(COLORS[position - 1]);

            return textViewPosition;
        }

    }

    private static final class PageAdapter extends FragmentStatePagerAdapter {

        public PageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            final Bundle bundle = new Bundle();
            bundle.putInt(PlaceholderFragment.EXTRA_POSITION, position + 1);

            final PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

    }
}
