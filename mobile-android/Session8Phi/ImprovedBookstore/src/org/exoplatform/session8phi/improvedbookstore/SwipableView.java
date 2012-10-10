package org.exoplatform.session8phi.improvedbookstore;

import org.exoplatform.session8phi.improvedbookstore.gesture.SwipeGestureListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SwipableView extends ViewGroup {

	private View detailsView;
	private View fullDescView;
    private GestureDetector gestureDetector;
	private SwipeGestureListener mListener;

	
	public SwipableView(Context context) {
		super(context);
		init();
	}

	public SwipableView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SwipableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		// Gesture detection
        mListener = new SwipeGestureListener(this);
        gestureDetector = new GestureDetector(getContext(), mListener);
        //If you are not using the long-press feature, it is a good idea to disable it
      	// The timers used can create some undesired behavior in handling other gestures.
        gestureDetector.setIsLongpressEnabled(false);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for(int i=0; i< this.getChildCount(); i++){
			View v = getChildAt(i);
			if (v instanceof RelativeLayout) {
				// set layout and variable of the details desc view
				detailsView = v;
				v.layout(l, t, r, b);
			} else if (v instanceof TextView) {
				// set layout and variable of the full desc view
				fullDescView = v;
				v.layout(r, t, r+r, b);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// set size of the child views
		for(int i=0; i<getChildCount(); i++){
	         View v = getChildAt(i);
	         v.measure(widthMeasureSpec, heightMeasureSpec);
	      }
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return gestureDetector.onTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			//If no child handles the initial touch, capture it
			return true;
		}
		if(gestureDetector.onTouchEvent(event)) {
			return true;
		}
		if(event.getAction() == MotionEvent.ACTION_UP) {
			// intercept when the user stops pressing the screen
            return mListener.handleScrollFinished();
        }
		return super.onTouchEvent(event);
	}
	
	public boolean handleBackButtonPressed() {
		return mListener.handleBackButtonPressed();
	}
	
	public void setDetailsView(View v) {
		detailsView = v;
	}
	public View getDetailsView() {
		return detailsView;
	}
	public void setFullDescView(View v) {
		fullDescView = v;
	}
	public View getFullDescView() {
		return fullDescView;
	}
}
