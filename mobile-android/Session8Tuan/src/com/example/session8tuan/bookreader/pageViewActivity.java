package com.example.session8tuan.bookreader;

import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

public class pageViewActivity extends PagerAdapter {

	public static int NUMBER_ITEMS = 10 ;

	public pageViewActivity() {
		// TODO Auto-generated constructor stub
	}

	 
	public int getCount() {
		// TODO Auto-generated method stub
		return NUMBER_ITEMS;
	}

	public Object instantiateItem(View collection, int position) {
		TextView tv = new TextView(Home.CONTEXT);
		tv.setText("Hello page# " + position);
		tv.setTextColor(Color.BLUE);
		tv.setTextSize(30);

		((ViewPager) collection).addView(tv,0);

		return tv;
	}

	 @Override
     public void destroyItem(View collection, int position, Object view) {
             ((ViewPager) collection).removeView((TextView) view);
     }

     
     
     @Override
     public boolean isViewFromObject(View view, Object object) {
             return view==((TextView)object);
     }
     @Override
		public void finishUpdate(View arg0) {}
		

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {}


}
