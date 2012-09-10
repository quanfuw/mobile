package com.example.com.exoplatform.session6tuan.sqllite;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {

	List<Contact>  list_ ;
	
	Context currentContext_ ;
	public ContactAdapter(Context c, List<Contact> data) {
		 currentContext_ = c ;
		 list_ = data;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView display = new TextView(currentContext_) ;
		display.setText(list_.get(position)._name) ;
		return display;
	}

}
