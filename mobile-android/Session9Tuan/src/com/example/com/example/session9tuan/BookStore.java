package com.example.com.example.session9tuan;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BookStore extends FragmentActivity implements ActionBar.TabListener, OnGestureListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
	 * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
	 * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;


	public static String BASE_URL = "int.exoplatform.org/rest/" ;
	public static String[] values = {"default"} ;
	static ArrayAdapter<String> listdata ;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_store);
		//listdata = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, values);
		//list = new ListView(this) ;    
		// Create the adapter that will return a fragment for each of the three primary sections
		// of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding tab.
		// We can also use ActionBar.Tab#select() to do this if we have a reference to the
		// Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);

			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by the adapter.
			// Also specify this Activity object, which implements the TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(
					actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

	}


	private ArrayAdapter<String> loadDataByThread() {
		//HttpRequest request = new HttpRequestExecutor() ;

		return  new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, values);
	}

	private ArrayAdapter<String> loadDataByAsynchTask() {

		return new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, values);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_book_store, menu);
		return true;
	}


	

	@Override
	protected void onResumeFragments() {
		// TODO Auto-generated method stub
		super.onResumeFragments();
	}


	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
		listdata = null;
		System.out.println("position ===== " + tab.getPosition());
		switch (tab.getPosition()) {
		case 0: {
			values = new String[] { "Android", "iPhone", "Linux", "OS/2" };
			listdata = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, values);
		break;}
		case 1:  {
			values = new String[]{"Activity","Document", "Gadget Appllication"};
			listdata = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, values);
			break;
		}
		
		case 2:	{
			values = new String[] { "Social", "Collaboration", "Knowledge","DMS", "WCM", "PLF", "Intranet"};
			listdata = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, values);		
			break;
		}
				
	 
		default: 
			values = new String[] {"default"};
			listdata = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, values);	
			break;
		}    
		//listdata = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, values);
		// list = (ListView)mSectionsPagerAdapter.getItem(tab.getPosition()).getView();
		;
		/*
    	TextView textView = new TextView(getActivity());

        textView.setGravity(Gravity.CENTER);
        Bundle args = getArguments();
        textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER)));
		 */
		//if(DummySectionFragment.list != null)
		//Fragment fm = mSectionsPagerAdapter.getItem(tab.getPosition()) ;
		//fm.onResume();

	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
	 * sections of the app.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0: return getString(R.string.title_section1).toUpperCase();
			case 1: return getString(R.string.title_section2).toUpperCase();
			case 2: return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		static ListView list ;

		public DummySectionFragment() {

		}

		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			list = new ListView(getActivity()) ;
			list.setAdapter(listdata);

			return list;
		}

		@Override
		public void onResume() {
			super.onResume();
			// TODO Auto-generated method stub
			if(list == null)list = new ListView(getActivity()) ;
			list.setAdapter(listdata);
			
		}


	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		System.out.println("=========== swap ui =============");
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
