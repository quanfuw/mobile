package com.example.com.exoplatform.session7tuan;

import java.util.List;

import com.example.com.exoplatform.session7tuan.data.Book;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class BookDetailAdapter  extends BaseAdapter {

	private List<String> data_ ;
	
	Context currentContext_ ;
	
	public BookDetailAdapter(Context currentContext, List<String> data) {
		currentContext_ = currentContext ;
		data_ = data ;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data_.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data_.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View currentContext, ViewGroup arg2) {
		TextView display = new TextView(currentContext_) ;
		display.setText(data_.get(position)) ;
		return display;
	}

}
