package com.ToxicBakery.viewpager.transforms.example;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;

public class MainActivity extends Activity {

	private static final String KEY_SELECTED_CLASS = "KEY_SELECTED_CLASS";

	private int mSelectedItem;
	private ViewPager mPager;
	private PageAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		mAdapter = new PageAdapter(getFragmentManager());

		mPager = (ViewPager) findViewById(R.id.container);
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				((PlaceholderFragment) mAdapter.instantiateItem(mPager, position)).setSelectedItem(mSelectedItem);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int position) {
			}
		});

		if (savedInstanceState != null) {
			mSelectedItem = savedInstanceState.getInt(KEY_SELECTED_CLASS);
		}

	}

	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(KEY_SELECTED_CLASS, mSelectedItem);
	}

	int getSelectedTransformerClass() {
		return mSelectedItem;
	}

	void setPageTransformer(int position, Class<? extends PageTransformer> transformerClass) {
		mSelectedItem = position;
		try {
			mPager.setPageTransformer(true, transformerClass.newInstance());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static class PlaceholderFragment extends Fragment implements OnItemClickListener {

		private ArrayAdapter<TransformerItem> mAdapter;
		private ArrayList<TransformerItem> mTransformers;
		private ListView mListView;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			mTransformers = new ArrayList<>();
			mTransformers.add(new TransformerItem(DefaultTransformer.class));
			mTransformers.add(new TransformerItem(AccordionTransformer.class));
			mTransformers.add(new TransformerItem(CubeInTransformer.class));
			mTransformers.add(new TransformerItem(CubeOutTransformer.class));
			mTransformers.add(new TransformerItem(FlipHorizontalTransformer.class));
			mTransformers.add(new TransformerItem(FlipVerticalTransformer.class));
			mTransformers.add(new TransformerItem(RotateDownTransformer.class));
			mTransformers.add(new TransformerItem(RotateUpTransformer.class));
			mTransformers.add(new TransformerItem(TabletTransformer.class));
			mTransformers.add(new TransformerItem(ZoomInTransformer.class));
			mTransformers.add(new TransformerItem(ZoomOutTranformer.class));

			mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_single_choice,
					android.R.id.text1, mTransformers);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			mListView = (ListView) inflater.inflate(R.layout.fragment_main, container, false);
			mListView.setAdapter(mAdapter);
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setOnItemClickListener(this);
			mListView.setItemChecked(((MainActivity) getActivity()).getSelectedTransformerClass(), true);

			int selectedItem = mListView.getCheckedItemPosition();
			((MainActivity) getActivity()).setPageTransformer(selectedItem, mAdapter.getItem(selectedItem).clazz);

			return mListView;
		}

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			((MainActivity) getActivity()).setPageTransformer(position, mAdapter.getItem(position).clazz);
		}

		public void setSelectedItem(int position) {
			if (mListView != null)
				mListView.setItemChecked(position, true);
		}

		private static final class TransformerItem {

			final String title;
			final Class<? extends PageTransformer> clazz;

			TransformerItem(Class<? extends PageTransformer> clazz) {
				this.clazz = clazz;
				title = clazz.getSimpleName();
			}

			@Override
			public String toString() {
				return title;
			}

		}

	}

	private static final class PageAdapter extends FragmentStatePagerAdapter {

		public PageAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int position) {
			return new PlaceholderFragment();
		}

		@Override
		public int getCount() {
			return 3;
		}

	}

}
